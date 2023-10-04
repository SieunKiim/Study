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
import billing.sieunojt.openfeign.payletter.PayletterFeign;
import billing.sieunojt.openfeign.payletter.dto.PayletterDto;
import billing.sieunojt.openfeign.payletter.mapper.PayletterMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PayletterPaymentServiceImpl implements PaymentService {
    @Value("${exchange.rate.exchange}")
    private int EXCHANGE_RATE;

    private final PayletterFeign payletterFeign;
    private final UserRepository userRepository;
    private final PayletterMapper payletterMapper;
    private final ExchangeOrderRepository exchangeOrderRepository;
    private final ExchangeRepository exchangeRepository;
    private final UserService userService;

    @Override
    public PgType getPgType() {
        return PgType.PAYLETTER;
    }

    @Override
    @Transactional
    public PaymentDto.PgPrepareResponse paymentPrepare(PaymentDto.PgPrepareRequest request) {
        String partnerOrderId = PaymentService.partnerOrderIdGenerator();
        String partnerUserId = PaymentService.partnerUserIdGenerator(request.getUser_id());
        PayletterDto.PrepareResponseDto prepareResponseDto = payletterFeign.preparePay(payletterMapper.toPrepareRequestDto(request, partnerOrderId, partnerUserId));
        ExchangeOrder payletter = ExchangeOrder.builder()
                .partnerOrderId(partnerOrderId)
                .partnerUserId(partnerUserId)
                .userId(request.getUser_id())
                .cash(request.getTotal_amount())
                .diamond(request.getTotal_amount() * EXCHANGE_RATE)
                .pgProvider("payletter")
                .isRefund(false)
                .build();

        exchangeOrderRepository.save(payletter);

        return new PaymentDto.PgPrepareResponse(prepareResponseDto.getOnline_url(), payletter.getPgProvider());
    }

    @Override
    public PaymentDto.PgApproveResponse paymentApprove(PaymentDto.PgApproveRequest request) {
        return null;
    }

    @Override
    @Transactional
    public PaymentDto.PgCancelResponse paymentCancel(PaymentDto.PgCancelRequest request) {
        if (exchangeRepository.existsByPartnerOrderIdAndIsRefund(request.getOrderId(),true)) {
            throw new ErrorException(ErrorType.ALREADY_REFUNDED_EXCHANGE);
        }
        Exchange exchange = exchangeRepository.findByPartnerOrderId(request.getOrderId()).orElseThrow(()-> new ErrorException(ErrorType.NOT_FOUND_EXCHANGE));
        ExchangeOrder exchangeOrder = exchangeOrderRepository.findByPartnerOrderId(request.getOrderId()).orElseThrow(() -> new ErrorException(ErrorType.NOT_FOUND_EXCHANGE));
        // 환불 Exchange 생성
        Exchange refundExchange = createRefundExchange(exchange);

        // 환불 Feign 요청
        PayletterDto.RefundResponseDto refund = payletterFeign.refund(payletterMapper.toRefundRequest(refundExchange, request.getUserIp(), exchangeOrder.getTid()));
        if (refund.getCode()!= null) {
            throw new ErrorException(ErrorType.EXCHANGE_REFUND_KAKAO_ISSUE, refund.getMessage() + "페이레터 이슈 이슈");
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
