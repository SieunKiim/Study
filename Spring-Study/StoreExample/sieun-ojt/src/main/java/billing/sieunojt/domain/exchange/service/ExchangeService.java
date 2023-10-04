package billing.sieunojt.domain.exchange.service;

import billing.sieunojt.common.exception.ErrorException;
import billing.sieunojt.common.exception.ErrorType;
import billing.sieunojt.domain.exchange.model.collection.AvailableExchanges;
import billing.sieunojt.domain.exchange.model.collection.BuyDiamondHistory;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.BuyDiamondsApprovalResponse;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.BuyDiamondsRequest;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.RefundDiamondRequest;
import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.exchange.model.entity.ExchangeOrder;
import billing.sieunojt.domain.exchange.repository.exchange.ExchangeRepository;
import billing.sieunojt.domain.exchange.repository.exchangeOrder.ExchangeOrderRepository;
import billing.sieunojt.domain.exchange.service.payment.PaymentService;
import billing.sieunojt.domain.exchange.service.payment.PaymentServiceFactory;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgApproveRequest;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgApproveResponse;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgCancelRequest;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgCancelResponse;
import billing.sieunojt.domain.exchange.service.payment.type.PgType;
import billing.sieunojt.domain.spend.model.collection.SpendExchangeInfos;
import billing.sieunojt.domain.spend.model.entity.Spend;
import billing.sieunojt.domain.user.service.UserService;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.Nio2Endpoint.Nio2SocketWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ExchangeOrderRepository exchangeOrderRepository;
    private final ExchangeRepository exchangeRepository;
    private final PaymentServiceFactory paymentServiceFactory;
    private final UserService userService;

    @Transactional
    public Exchange save(Exchange exchange) {
        return exchangeRepository.save(exchange);
    }

    @Transactional(readOnly = true)
    public Exchange findById(Long exchangeId) {
        return exchangeRepository.findById(exchangeId)
                .orElseThrow(() -> new ErrorException(ErrorType.NOT_FOUND_EXCHANGE));
    }

    @Transactional
    public PaymentDto.PgPrepareResponse buyDiamondsPrepare(BuyDiamondsRequest request) {
        PaymentService paymentService = paymentServiceFactory.getPgPaymentServiceImpl(
                request.getPg());
        return paymentService.paymentPrepare(new PaymentDto.PgPrepareRequest(request));
    }

    @Transactional
    public BuyDiamondsApprovalResponse buyDiamondApproval(PgType pg, String pgToken,
            String orderId) {
        PaymentService paymentService = paymentServiceFactory.getPgPaymentServiceImpl(pg);
        PgApproveRequest pgApproveRequest = PgApproveRequest.from(pg, pgToken, orderId);
        PgApproveResponse pgApproveResponse = paymentService.paymentApprove(pgApproveRequest);

        return BuyDiamondsApprovalResponse.from(pgApproveResponse);
    }

    @Transactional
    public ExchangeDto.RefundDiamondResponse exchangeRefund(RefundDiamondRequest request,
            String ip) {
        PaymentService paymentService = paymentServiceFactory.getPgPaymentServiceImpl(
                request.getPg());

        PgCancelResponse pgCancelResponse = paymentService.paymentCancel(
                PgCancelRequest.from(request, ip)
        );
        return ExchangeDto.RefundDiamondResponse.from(pgCancelResponse);
    }

    @Transactional(readOnly = true)
    public AvailableExchanges findAvailableExchange(Long userId) {
        return new AvailableExchanges(
                exchangeRepository.findAvailableExchange(userId));
    }

    /**
     * Just for payLetter
     */
    @Transactional
    public void payletterInsteadCallback(ExchangeDto.PayletterReturnValueDto request) {
        ExchangeOrder exchangeOrder = exchangeOrderRepository.findByPartnerOrderId(
                        request.getOrder_no())
                .orElseThrow(() -> new ErrorException(ErrorType.NOT_FOUND_EXCHANGE_ORDER));
        if (exchangeOrder.getSuccessCode() != null) {
            throw new ErrorException(ErrorType.ALREADY_APPROVED_EXCHANGE);
        }
        exchangeOrder.payApproval(request.getCid(), request.getTid());
        exchangeRepository.save(Exchange.createBuyExchangeFromExchangeOrder(exchangeOrder));
        userService.depositDiamond(exchangeOrder.getUserId(), exchangeOrder.getDiamond());
    }

    @Transactional
    public void processExpiration(Exchange exchange, Integer amount) {
        exchange.useLeftDiamond(amount);
    }

    @Transactional(readOnly = true)
    public BuyDiamondHistory getBuyDiamondExchanges(Long userId) {
        return new BuyDiamondHistory(exchangeRepository.findAllBuyExchanges(userId));
    }

    @Transactional(readOnly = true)
    public List<ExchangeDto.RefundDiamondHistoryResponse> getRefundDiamondExchanges(Long userId) {
        userService.isExistUser(userId);
        return exchangeRepository.findAllRefundExchanges(userId);
    }

    @Transactional
    public void revertExchangeUsage(SpendExchangeInfos spendExchangeInfos) {
        Map<Long, Exchange> exchangeMap = spendExchangeInfos.getExchanges();
        for (Spend spend : spendExchangeInfos.getSpends()) {
            Exchange exchange = exchangeMap.get(spend.getExchangeId());
            exchange.recoverUsedLeftDiamond(spend.getUseAmount());
        }
    }

    public List<Exchange> getAllInExchangeIds(List<Long> ids){
        return exchangeRepository.findAllByIdIn(ids);
    }
}
