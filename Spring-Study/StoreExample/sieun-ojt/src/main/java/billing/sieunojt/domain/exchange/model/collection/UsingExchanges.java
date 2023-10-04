package billing.sieunojt.domain.exchange.model.collection;

import billing.sieunojt.domain.exchange.model.entity.Exchange;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class UsingExchanges {
    private final List<Exchange> usingExchanges;
    private final Integer leftDiamondSum;

    public UsingExchanges(List<Exchange> exchanges, Integer cost) {
        usingExchanges = new ArrayList<>();
        for (Exchange ex : exchanges) {
            if (cost <= 0) {
                break;
            }
            this.usingExchanges.add(ex);
            cost -= ex.getLeftDiamond();
        }
        leftDiamondSum = calculateLeftDiamondSum();
    }

    public List<Exchange> getExchangesExceptLast() {
        return usingExchanges.subList(0, usingExchanges.size() - 1);
    }

    public Exchange getLastExchange() {
        return usingExchanges.get(usingExchanges.size() - 1);
    }

    public Integer calculateLastExchangeUsage(Integer totalCost){
        Exchange ex = getLastExchange();
        return ex.getLeftDiamond() - (this.leftDiamondSum - totalCost);
    }

    private Integer calculateLeftDiamondSum(){
        return this.usingExchanges.stream().mapToInt(Exchange::getLeftDiamond).sum();
    }
}
