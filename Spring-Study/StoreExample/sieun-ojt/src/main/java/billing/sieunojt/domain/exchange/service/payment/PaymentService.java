package billing.sieunojt.domain.exchange.service.payment;


import billing.sieunojt.common.exception.ErrorException;
import billing.sieunojt.common.exception.ErrorType;
import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.exchange.model.entity.ExchangeOrder;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto;
import billing.sieunojt.domain.exchange.service.payment.type.PgType;
import billing.sieunojt.domain.user.model.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PaymentService {
    PgType getPgType();
    PaymentDto.PgPrepareResponse paymentPrepare(PaymentDto.PgPrepareRequest request);

    PaymentDto.PgApproveResponse paymentApprove(PaymentDto.PgApproveRequest request);

    PaymentDto.PgCancelResponse paymentCancel(PaymentDto.PgCancelRequest request);

    static String partnerOrderIdGenerator(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    static String partnerUserIdGenerator(long userId){
        return String.valueOf(userId);
    }

    static Exchange createExchange(ExchangeOrder exchangeOrder) {
        return Exchange.builder()
                .partnerOrderId(exchangeOrder.getPartnerOrderId())
                .userId(exchangeOrder.getUserId())
                .cash(exchangeOrder.getCash())
                .diamond(exchangeOrder.getDiamond())
                .isRefund(exchangeOrder.isRefund())
                .exchangeCharge(0)
                .leftDiamond(exchangeOrder.getDiamond())
                .targetRefundId(0L)
                .expiredAt(LocalDateTime.now().plusDays(5))
                .build();
    }

    static void checkRefundValidation(User user, Exchange exchange) { // 환불 정책
        // 잔여 다이아몬드랑 비교
        if (user.getDiamondBalance() < exchange.getDiamond()) {
            throw new ErrorException(ErrorType.NOT_ENOUGH_DIAMOND);
        }

        if (!exchange.getLeftDiamond().equals(exchange.getDiamond())) {
            throw new ErrorException(ErrorType.EXCHANGE_REFUND_INVALID);
        }
    }

}
