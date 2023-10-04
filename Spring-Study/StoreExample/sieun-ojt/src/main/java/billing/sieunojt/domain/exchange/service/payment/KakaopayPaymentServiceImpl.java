package billing.sieunojt.domain.exchange.service.payment;

import billing.sieunojt.common.exception.ErrorException;
import billing.sieunojt.common.exception.ErrorType;
import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.exchange.model.entity.ExchangeOrder;
import billing.sieunojt.domain.exchange.repository.exchange.ExchangeRepository;
import billing.sieunojt.domain.exchange.repository.exchangeOrder.ExchangeOrderRepository;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto;
import billing.sieunojt.domain.exchange.service.payment.type.PgType;
import billing.sieunojt.domain.user.model.entity.User;
import billing.sieunojt.domain.user.repository.UserRepository;
import billing.sieunojt.domain.user.service.UserService;
import billing.sieunojt.openfeign.kakaoPay.KakaoPayFeign;
import billing.sieunojt.openfeign.kakaoPay.dto.KakaoPayDto;
import billing.sieunojt.openfeign.kakaoPay.mapper.KakaoPayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class KakaopayPaymentServiceImpl implements PaymentService {
    @Value("${exchange.rate.exchange}")
    private int EXCHANGE_RATE;

    private final KakaoPayMapper kakaoPayMapper;
    private final KakaoPayFeign kakaoPayFeign;
    private final ExchangeOrderRepository exchangeOrderRepository;
    private final ExchangeRepository exchangeRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public PgType getPgType() {
        return PgType.KAKAOPAY;
    }

    @Override
    @Transactional
    public PaymentDto.PgPrepareResponse paymentPrepare(PaymentDto.PgPrepareRequest request) {

        String partnerOrderId = PaymentService.partnerOrderIdGenerator();
        KakaoPayDto.PrepareResponseDto prepareResponseDto = kakaoPayFeign.preparePay(kakaoPayMapper.toPrepareRequestDto(request, partnerOrderId));
        ExchangeOrder kakaopay = ExchangeOrder.builder()
                .partnerOrderId(partnerOrderId)
                .tid(prepareResponseDto.getTid())
                .cash(request.getTotal_amount())
                .pgProvider("kakaopay")
                .isRefund(false)
                .userId(request.getUser_id())
                .partnerUserId(PaymentService.partnerUserIdGenerator(request.getUser_id()))
                .diamond(request.getTotal_amount() * EXCHANGE_RATE)
                .build();

        exchangeOrderRepository.save(kakaopay);

        return new PaymentDto.PgPrepareResponse(prepareResponseDto.getNext_redirect_pc_url(), kakaopay.getPgProvider());
    }

    @Override
    @Transactional
    public PaymentDto.PgApproveResponse paymentApprove(PaymentDto.PgApproveRequest request) {
        ExchangeOrder exchangeOrder = exchangeOrderRepository.findByPartnerOrderId(request.getOrderId())
                .orElseThrow(() -> new ErrorException(ErrorType.NOT_FOUND_EXCHANGE));
        User user = userRepository.findById(exchangeOrder.getUserId())
                .orElseThrow(() -> new ErrorException(ErrorType.NOT_FOUND_USER));
        // 결제 승인 요청 API 호출
        KakaoPayDto.ApprovalResponseDto approvalResult = kakaoPayFeign.approvalPay(kakaoPayMapper.toApprovalRequestDto(exchangeOrder, request.getPgToken()));

        // orderId로 엔티티 조회하여 기존 금액과 일치하는지 대조
        if(exchangeOrder.getCash() != approvalResult.getAmount().getTotal()){
            throw new ErrorException(ErrorType.EXCHANGE_VALUE_INCONSISTENCY);
        }
        // user balance 에 반영
        int diamondBalance = userService.depositDiamond(user.getId(), exchangeOrder.getDiamond());
        exchangeOrder.payApproval(approvalResult.getAid());
        exchangeRepository.save(PaymentService.createExchange(exchangeOrder));
        // 결제 결과 반환
        return PaymentDto.PgApproveResponse.builder()
                .depositedDiamond(exchangeOrder.getDiamond())
                .usedCash(exchangeOrder.getCash())
                .userId(user.getId())
                .diamondBalance(diamondBalance)
                .build();
    }

    @Override
    @Transactional
    public PaymentDto.PgCancelResponse paymentCancel(PaymentDto.PgCancelRequest request) {
        if (exchangeRepository.existsByPartnerOrderIdAndIsRefund(request.getOrderId(), true)) {
            throw new ErrorException(ErrorType.ALREADY_REFUNDED_EXCHANGE);
        }

        // 지난 결제 정보 조회
        Exchange exchange = exchangeRepository.findByPartnerOrderId(request.getOrderId()).orElseThrow(()-> new ErrorException(ErrorType.NOT_FOUND_EXCHANGE));
        ExchangeOrder exchangeOrder = exchangeOrderRepository.findByPartnerOrderId(request.getOrderId()).orElseThrow(() -> new ErrorException(ErrorType.NOT_FOUND_EXCHANGE_ORDER));
        // 환불 Exchange 생성
        Exchange refundExchange = createRefundExchange(exchange);

        // 환불 Feign 요청
        KakaoPayDto.RefundResponseDto refund = kakaoPayFeign.refund(kakaoPayMapper.toRefundRequest(refundExchange, exchangeOrder.getTid()));
        if (!refund.getStatus().contains("CANCEL_PAYMENT")) {
            throw new ErrorException(ErrorType.EXCHANGE_REFUND_KAKAO_ISSUE, refund.getStatus() + "카카오페이 이슈");
        }

        // 정상 환불 완료 시 (사용자 Balance 적용 & Exchange 저장)
        exchangeRepository.save(refundExchange);
        exchange.useAllLeftDiamond();
        int userBalance = userService.withdrawDiamond(refundExchange.getUserId(), refundExchange.getDiamond());
        return PaymentDto.PgCancelResponse.builder()
                .cash(refundExchange.getCash() - refundExchange.getExchangeCharge())
                .orderId(refundExchange.getPartnerOrderId())
                .userBalance(userBalance)
                .diamond(refundExchange.getDiamond())
                .build();
    }

    private Exchange createRefundExchange(Exchange exchange) {
        int refundCash = exchange.getDiamond()/EXCHANGE_RATE;
        User user = userRepository.findById(exchange.getUserId()).orElseThrow(() -> new ErrorException(ErrorType.NOT_FOUND_USER));
        //환불 정책
        PaymentService.checkRefundValidation(user, exchange);
        return Exchange.builder()
                .partnerOrderId(exchange.getPartnerOrderId())
                .userId(exchange.getUserId())
                .cash(refundCash)
                .diamond(exchange.getDiamond())
                .isRefund(true)
                .exchangeCharge(refundCash /10)
                .leftDiamond(0)
                .targetRefundId(exchange.getId())
                .build();
    }
}
