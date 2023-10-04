package billing.sieunojt.openfeign.kakaoPay.dto;

import billing.sieunojt.domain.exchange.model.entity.ExchangeOrder;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

public class KakaoPayDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PrepareRequestDto {
        private String cid; //가맹점 코드
        private String partner_order_id; //가맹점 주문번호
        private String partner_user_id; //가맹점 회원 id
        private String item_name; //상품명
        private Integer quantity; //상품 수량
        private Integer total_amount; //상품 총액
        private Integer tax_free_amount; // 상품 비과세 금액
        private String approval_url; //결제 성공 시 redirect url
        private String cancel_url; //결제 취소 시 redirect url
        private String fail_url; //결제 실패 시 redirect url

        public PrepareRequestDto(String cid, String partner_order_id, String partner_user_id, Integer total_amount, String approval_url, String cancel_url, String fail_url) {
            this.cid = cid;
            this.partner_order_id = partner_order_id;
            this.partner_user_id = partner_user_id;
            this.item_name = "diamond";
            this.quantity = 1;
            this.total_amount = total_amount;
            this.tax_free_amount = 0;
            this.approval_url = approval_url;
            this.cancel_url = cancel_url;
            this.fail_url = fail_url;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class PrepareResponseDto{
        private String tid;
        private String next_redirect_app_url;
        private String next_redirect_mobile_url;
        private String next_redirect_pc_url;
        private String android_app_scheme;
        private String ios_app_scheme;
        private Date created_at;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ApprovalRequestDto {
        private String cid;
        private String tid;
        private String partner_order_id;
        private String partner_user_id;
        private String pg_token;

        public ApprovalRequestDto(String cid, String pgToken, ExchangeOrder exchangeOrder) {
            this.cid = cid;
            this.tid = exchangeOrder.getTid();
            this.partner_order_id = exchangeOrder.getPartnerOrderId();
            this.partner_user_id = exchangeOrder.getPartnerUserId();
            this.pg_token = pgToken;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ApprovalResponseDto{

        private String aid;
        private String tid;
        private String cid;
        private String sid;
        private String partner_order_id;
        private String partner_user_id;
        private String payment_method_type;

        private Amount amount;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        private CardInfo card_info;

        private String item_name;
        private String item_code;
        private String quantity;
        private String created_at;
        private String approved_at;
        private String payload;


       @Getter
       @NoArgsConstructor
        public static class CardInfo{
            private String  purchase_corp;
            private String  purchase_corp_code;
            private String  issuer_corp;
            private String  issuer_corp_code;
            private String  kakaopay_purchase_corp;
            private String  kakaopay_purchase_corp_code;
            private String  kakaopay_issuer_corp;
            private String  kakaopay_issuer_corp_code;
            private String  bin;
            private String  card_type;
            private String  install_month;
            private String  approved_id	;
            private String  card_mid;
            private String  interest_free_install;
            private String  card_item_code;
        }
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class RefundRequestDto{
        private String cid;
        private String tid;
        private String orderId;
        private int cancel_amount;
        private int cancel_tax_free_amount;
    }

    @Getter
    @NoArgsConstructor
    public static class RefundResponseDto{
        private String aid;
        private String tid;
        private String cid;
        private String status;
        private String partner_order_id;
        private String partner_user_id;
        private String payment_method_type;
        private Amount amount;
        private ApprovedCancelAmount approved_cancel_amount;
        private CanceledAmount canceled_amount;
        private CancelAvailableAmount cancel_available_amount;
        private String item_name;
        private String item_code;
        private int quantity;
        private Date created_at;
        private Date approved_at;
        private Date canceled_at;
        private String payload;
    }

    @Getter
    @NoArgsConstructor
    public static class Amount{
        private int total;
        private int tax_free;
        private int vat;
        private int point;
        private int discount;
        private int green_deposit;
    }

    @Getter
    @NoArgsConstructor
    public static class ApprovedCancelAmount{ // 이번 취소 건 금액 정보
        private int total;
        private int tax_free;
        private int vat;
        private int point;
        private int discount;
        private int green_deposit;
    }

    @Getter
    @NoArgsConstructor
    public static class CanceledAmount{ // 누적 취소 금액 정보
        private int total;
        private int tax_free;
        private int vat;
        private int point;
        private int discount;
        private int green_deposit;
    }

    @Getter
    @NoArgsConstructor
    public static class CancelAvailableAmount{ // 취소 가능 금액 정보
        private int total;
        private int tax_free;
        private int vat;
        private int point;
        private int discount;
        private int green_deposit;
    }
}
