package billing.sieunojt.openfeign.kakaoPay.mapper;

import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.exchange.model.entity.ExchangeOrder;
import billing.sieunojt.domain.exchange.service.payment.PaymentService;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto;
import billing.sieunojt.openfeign.kakaoPay.dto.KakaoPayDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class KakaoPayMapper {
    @Value("${pg.kakao-pay.approval-url}")
    private String APPROVAL_URL;

    @Value("${pg.kakao-pay.cancel-url}")
    private String CANCEL_URL;

    @Value("${pg.kakao-pay.fail-url}")
    private String FAIL_URL;

    @Value("${pg.kakao-pay.CID}")
    private String CID;

    public KakaoPayDto.PrepareRequestDto toPrepareRequestDto(PaymentDto.PgPrepareRequest request, String partnerOrderId) {
        String approval_url = APPROVAL_URL + "?order_id=" + partnerOrderId + "&pg=" + request.getPgType().name();
        return new KakaoPayDto.PrepareRequestDto(CID, partnerOrderId, PaymentService.partnerUserIdGenerator(request.getUser_id()), request.getTotal_amount(), approval_url, CANCEL_URL, FAIL_URL);
    }

    public KakaoPayDto.ApprovalRequestDto toApprovalRequestDto(ExchangeOrder exchangeOrder, String pgToken) {
        return KakaoPayDto.ApprovalRequestDto.builder()
                .cid(CID)
                .partner_order_id(exchangeOrder.getPartnerOrderId())
                .partner_user_id(exchangeOrder.getPartnerUserId())
                .pg_token(pgToken)
                .tid(exchangeOrder.getTid())
                .build();
    }

    public KakaoPayDto.RefundRequestDto toRefundRequest(Exchange exchange, String tid){
        return KakaoPayDto.RefundRequestDto.builder()
                .cid(CID)
                .tid(tid)
                .orderId(exchange.getPartnerOrderId())
                .cancel_amount(exchange.getCash())
                .cancel_tax_free_amount(0)
                .build();
    }
}
