package billing.sieunojt.openfeign.payletter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PayletterDto {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PrepareRequestDto {
        private String pgcode;
        private String client_id;
        private String order_no;
        private String user_id;
        private Number amount;
        private String product_name;
        private String return_url;
        private String callback_url;
    }

    @Getter
    @NoArgsConstructor
    public static class PrepareResponseDto{
        private Number token;
        private String online_url;
        private String mobile_url;
        private Number code;
        private String message;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundRequestDto{
        private String pgcode;
        private String tid;
        private String client_id;
        private String user_id;
        private String order_id;
        private String ip_addr;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundResponseDto{
        private String tid;
        private String cid; // 승인번호
        private Number amount;
        private String cancel_date;
        private Number code;
        private String message;
    }
}
