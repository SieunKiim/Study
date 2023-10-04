package billing.sieunojt.openfeign.payletter.mapper;

import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto;
import billing.sieunojt.openfeign.kakaoPay.dto.KakaoPayDto;
import billing.sieunojt.openfeign.payletter.dto.PayletterDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PayletterMapper {
    @Value("${pg.payletter.CID}")
    private String CID;

    @Value("${pg.payletter.return-url}")
    private String returnUrl;

    @Value("${pg.payletter.call-back}")
    private String callbackUrl;

    public PayletterDto.PrepareRequestDto toPrepareRequestDto(PaymentDto.PgPrepareRequest request, String partnerOrderId, String partnerUserId) {
        request.setQuantity(1);
        request.setItem_name("다이아 구매");
        request.setCallback_url(callbackUrl);
        request.setReturn_url(returnUrl);
        return PayletterDto.PrepareRequestDto.builder()
                .pgcode(request.getPgcode())
                .client_id(CID)
                .user_id(partnerUserId)
                .order_no(partnerOrderId)
                .amount(request.getTotal_amount())
                .product_name(request.getItem_name())
                .return_url(request.getReturn_url())
                .callback_url(request.getCallback_url())
                .build();
    }

    public PayletterDto.RefundRequestDto toRefundRequest(Exchange exchange, String userIp, String tid){
        return PayletterDto.RefundRequestDto.builder()
                .tid(tid)
                .order_id(exchange.getPartnerOrderId())
                .client_id(CID)
                .user_id(String.valueOf(exchange.getUserId()))
                .ip_addr(userIp)
                .build();
    }

}
