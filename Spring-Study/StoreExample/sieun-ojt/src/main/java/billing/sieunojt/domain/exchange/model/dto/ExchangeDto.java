package billing.sieunojt.domain.exchange.model.dto;

import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgApproveResponse;
import billing.sieunojt.domain.exchange.service.payment.dto.PaymentDto.PgCancelResponse;
import billing.sieunojt.domain.exchange.service.payment.type.PgType;
import lombok.*;

import java.time.LocalDateTime;

public class ExchangeDto {

    @Getter
    @NoArgsConstructor
    public static class BuyDiamondsRequest {
        private long user_id;
        private int amount;
        private PgType pg;
        private String pg_code;
    }

    @Builder
    @AllArgsConstructor
    public static class BuyDiamondsResponse { // 사용자 id, 보유 다이아몬드, 다이아몬드 변동량
        private long user_id;
        private int diamond_balance;
        private int balance_change;
    }

    @Getter
    @NoArgsConstructor
    public static class BuyDiamondsApprovalRequest {
        private long user_id;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BuyDiamondsApprovalResponse {
        private long user_id;
        private int used_cash;
        private int diamond_balance;

        public static BuyDiamondsApprovalResponse from(PgApproveResponse ar) {
            return BuyDiamondsApprovalResponse.builder()
                    .used_cash(ar.getUsedCash())
                    .diamond_balance(ar.getDiamondBalance())
                    .user_id(ar.getUserId())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class RefundDiamondRequest{
        private PgType pg;
        private String order_id;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundDiamondResponse {
        private int cash;
        private int diamond;
        private int user_balance;
        private String orderId;

        public static RefundDiamondResponse from(PgCancelResponse pcr) {
            return RefundDiamondResponse.builder()
                    .cash(pcr.getCash())
                    .user_balance(pcr.getUserBalance())
                    .diamond(pcr.getDiamond())
                    .orderId(pcr.getOrderId())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class BuyDiamondHistoryResponse{
        private int cash;
        private int diamond;
        private LocalDateTime date;
    }

    @Getter
    @NoArgsConstructor
    public static class RefundDiamondHistoryResponse{
        private int cash;
        private int diamond;
        private LocalDateTime date;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PayletterReturnValueDto{
        private String code;
        private String message;
        private String user_id;
        private String user_name;
        private String order_no;
        private String service_name;
        private String product_name;
        private String custom_parameter;
        private String tid;
        private String cid;
        private Number amount;
        private Number taxfree_amount;
        private Number tax_amount;
        private String pay_info;
        private String pgcode;
        private String billkey;
        private String domestic_flag;
        private String transaction_date;
        private Number install_month;
        private String card_info;
        private String payhash;
        private Number disposable_cup_deposit;
    }
}
