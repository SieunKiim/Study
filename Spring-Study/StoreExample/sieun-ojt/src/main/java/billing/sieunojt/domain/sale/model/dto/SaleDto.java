package billing.sieunojt.domain.sale.model.dto;

import billing.sieunojt.domain.sale.model.entity.Sale;
import billing.sieunojt.domain.spend.model.collection.SpendExchangeInfos;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SaleDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BuyItemRequest{
        private Long user_id;
        private Long item_id;
        private Integer amount;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BuyItemResponse{
        private Long user_id;
        private Long sale_id;
        private String item_name;
        private Integer cost;
        private Integer left_balance;

        public BuyItemResponse(Sale sale, String item_name, Integer leftBalance) {
            this.user_id = sale.getUserId();
            this.sale_id = sale.getId();
            this.item_name = item_name;
            this.cost = sale.getTotalCost();
            this.left_balance = leftBalance;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RefundItemRequest{
        private long user_id;
        private long sale_id;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RefundItemResponse{
        private long userId;
        private String item_name;
        private int amount;
        private int total_cost;

        public static RefundItemResponse from(SaleItemInfo si) {
            return RefundItemResponse.builder()
                    .userId(si.getUserId())
                    .item_name(si.getItemName())
                    .amount(si.getAmount())
                    .total_cost(si.getTotalCost())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class BuyHistoryResponse{
        private long sale_id;
        private long user_id;
        private ItemDto item;
        private int amount;
        private int total_cost;

        @QueryProjection
        public BuyHistoryResponse(long sale_id, long user_id, long item_id, String item_name, int diamond, int amount, int total_cost) {
            this.sale_id = sale_id;
            this.user_id = user_id;
            this.item = new ItemDto(item_id, item_name, diamond);
            this.amount = amount;
            this.total_cost = total_cost;
        }
    }

    @Getter
    @NoArgsConstructor
    public static class RefundHistoryResponse{
        private long sale_id;
        private long user_id;
        private ItemDto item;
        private int amount;
        private int total_cost;

        @QueryProjection
        public RefundHistoryResponse(long sale_id, long user_id, long item_id, String item_name, int diamond, int amount, int total_cost) {
            this.sale_id = sale_id;
            this.user_id = user_id;
            this.item = new ItemDto(item_id, item_name, diamond);
            this.amount = amount;
            this.total_cost = total_cost;
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ItemDto{
        private long item_id;
        private String item_name;
        private int diamond;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BuyItemInfo {
        private Long userId;
        private Long itemId;
        private Integer userDiamondBalance;
        private Integer itemCost;
        private String itemName;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaleRequires{
        private Long userId;
        private Long itemId;
        private Integer userDiamondBalance;
        private Integer itemCost;
        private String itemName;
        private Integer totalCost;
        private Integer amount;

        public SaleRequires(BuyItemInfo bi, Integer amount) {
            this.userId = bi.getUserId();
            this.itemId = bi.getItemId();
            this.userDiamondBalance = bi.getUserDiamondBalance();
            this.itemCost = bi.getItemCost();
            this.itemName = bi.getItemName();
            this.totalCost = Sale.calculateTotalCost(bi.getItemCost(), amount);
            this.amount = amount;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SaleItemInfo{
        private Long saleId;
        private Integer amount;
        private Integer totalCost;
        private Long itemId;
        private String itemName;
        private Integer itemCost;
        private Long userId;
    }
}
