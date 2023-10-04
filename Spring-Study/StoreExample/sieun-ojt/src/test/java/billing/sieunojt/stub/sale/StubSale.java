package billing.sieunojt.stub.sale;

import billing.sieunojt.domain.sale.model.dto.SaleDto.BuyItemRequest;

public class StubSale {
    public static class BuyItemRequest{
        private Long user_id = 1L;
        private Long item_id = 1L;
        private Integer Amount = 3;
    }
}
