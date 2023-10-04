package billing.sieunojt.domain.spend.model.dto;

import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.spend.model.entity.Spend;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class SpendDto {
    @Getter
    @NoArgsConstructor
    public static class SpendExchangeDto{
        private Exchange exchange;
        private Spend spend;
    }

}
