package billing.sieunojt.domain.spend.model.collection;

import billing.sieunojt.common.exception.ErrorException;
import billing.sieunojt.common.exception.ErrorType;
import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.spend.model.dto.SpendDto.SpendExchangeDto;
import billing.sieunojt.domain.spend.model.entity.Spend;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class SpendExchangeInfos {
    private final Map<Long, Exchange> exchanges;
    private final List<Spend> spends;
    private final Integer totalSpendSum;

    public SpendExchangeInfos(List<SpendExchangeDto> spendInfos){
        validateNull(spendInfos);
        this.exchanges = spendInfos.stream().map(SpendExchangeDto::getExchange)
                .collect(Collectors.toMap(Exchange::getId, exchange -> exchange));
        this.spends = spendInfos.stream().map(SpendExchangeDto::getSpend).collect(Collectors.toList());
        this.totalSpendSum = sumSpend();
    }

    private void validateNull(List<SpendExchangeDto> spendInfos){
        if (spendInfos == null || spendInfos.size() == 0) {
            throw new ErrorException(ErrorType.NOT_FOUND_SALE);
        }
    }

    private Integer sumSpend(){
        int sum = 0;
        for (Spend spendInfo : this.spends) {
            sum += spendInfo.getUseAmount();
        }
        return sum;
    }
}
