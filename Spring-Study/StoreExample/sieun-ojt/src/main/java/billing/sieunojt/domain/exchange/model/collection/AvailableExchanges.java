package billing.sieunojt.domain.exchange.model.collection;

import billing.sieunojt.domain.exchange.model.entity.Exchange;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AvailableExchanges {

    private final Long userId;
    private final List<Exchange> exchanges;
    private final Integer size;

    public AvailableExchanges(List<Exchange> exchanges) {
        this.exchanges = exchanges;
        this.size = exchanges.size();
        this.userId = getFirstExchange().getUserId();
    }

    private Exchange getFirstExchange() {
        return this.exchanges.get(0);
    }

    public UsingExchanges getUsingExchanges(int cost){
        return new UsingExchanges(this.exchanges, cost);
    }
}
