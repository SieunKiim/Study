package billing.sieunojt.domain.exchange.service.payment.dto;

import billing.sieunojt.domain.exchange.model.dto.ExchangeDto;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.RefundDiamondRequest;
import billing.sieunojt.domain.exchange.service.payment.type.PgType;
import javax.servlet.http.HttpServletRequest;
import lombok.*;

public class PaymentDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PgPrepareRequest {
        private String pgcode; // 결제 수단 코드
        private String pg;
        private String cid; //가맹점 코드
        private String partner_order_id; //가맹점 주문 번호
        private String partner_user_id; //가맹점 회원 id
        private String item_name; //상품명
        private Integer quantity; //상품 수량
        private Integer total_amount; //상품 총액
        private Integer tax_free_amount; // 상품 비과세 금액
        private String approval_url; //결제 성공 시 redirect url
        private String cancel_url; //결제 취소 시 redirect url
        private String fail_url; //결제 실패 시 redirect url
        private String return_url;
        private String callback_url; // 콜백 url (결제 성공 결과를 수신할 callback URL)
        private Long user_id;
        private PgType pgType;

        public PgPrepareRequest(ExchangeDto.BuyDiamondsRequest request) {
            this.user_id = request.getUser_id();
            this.total_amount = request.getAmount();
            this.pg = request.getPg().getPgName();
            this.pgcode = request.getPg_code();
            this.pgType = request.getPg();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PgPrepareResponse {
        private String approve_url;
        private String pgProvider;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PgApproveRequest {
        private String pg;
        private String pgToken;
        private String orderId;

        public static PgApproveRequest from(PgType pg, String pgToken, String orderId) {
            return PgApproveRequest.builder()
                    .orderId(orderId)
                    .pg(pg.getPgName())
                    .pgToken(pgToken)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PgApproveResponse {
        private Long userId;
        private Integer usedCash;
        private Integer depositedDiamond;
        private Integer diamondBalance;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PgCancelRequest {
        private String orderId;
        private String userIp;

        public static PgCancelRequest from(RefundDiamondRequest request, String userIp) {
            return PgCancelRequest.builder()
                    .orderId(request.getOrder_id())
                    .userIp(userIp)
                    .build();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PgCancelResponse {
        private String orderId;
        private int cash;
        private int userBalance;
        private int diamond;
    }

}
