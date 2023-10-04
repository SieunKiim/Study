package billing.sieunojt.domain.exchange.service.payment;

import billing.sieunojt.domain.exchange.service.payment.type.PgType;
import java.util.EnumMap;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFactory {
    private final EnumMap<PgType, PaymentService> pgPaymentServiceMap = new EnumMap<>(PgType.class);

    public PaymentServiceFactory(List<PaymentService> paymentServices){
        paymentServices.forEach(paymentService -> pgPaymentServiceMap.put(paymentService.getPgType(), paymentService));
    }

    public PaymentService getPgPaymentServiceImpl(PgType pgType) {
        return pgPaymentServiceMap.get(pgType);
    }
}
