package billing.sieunojt.fake.factory;

import billing.sieunojt.domain.exchange.service.payment.KakaopayPaymentServiceImpl;
import billing.sieunojt.domain.exchange.service.payment.PayletterPaymentServiceImpl;
import billing.sieunojt.domain.exchange.service.payment.PaymentService;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgApproveRequest;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgApproveResponse;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgCancelRequest;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgCancelResponse;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgPrepareRequest;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgPrepareResponse;
import billing.sieunojt.domain.exchange.service.payment.type.PgType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lombok.Getter;

@Getter
public class FakePaymentService implements PaymentService {

    private final List<PaymentService> paymentServiceList = new ArrayList<>();

    public FakePaymentService(KakaopayPaymentServiceImpl kakao, PayletterPaymentServiceImpl payletter){
        paymentServiceList.add(kakao);
        paymentServiceList.add(payletter);
    }

    @Override
    public PgType getPgType() {
        return null;
    }

    @Override
    public PgPrepareResponse paymentPrepare(PgPrepareRequest request) {
        return null;
    }

    @Override
    public PgApproveResponse paymentApprove(PgApproveRequest request) {
        return null;
    }

    @Override
    public PgCancelResponse paymentCancel(PgCancelRequest request) {
        return null;
    }
}
