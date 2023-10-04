package billing.sieunojt.domain.exchange.model.collection;

import billing.sieunojt.common.exception.ErrorException;
import billing.sieunojt.common.exception.ErrorType;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.BuyDiamondHistoryResponse;
import java.util.List;
import lombok.Getter;

@Getter
public class BuyDiamondHistory {
    private final List<BuyDiamondHistoryResponse> buyDiamondHistory;

    public BuyDiamondHistory(List<BuyDiamondHistoryResponse> buyDiamondHistory) {
        checkBuyDiamondHistory(buyDiamondHistory);
        this.buyDiamondHistory = buyDiamondHistory;

    }

    private void checkBuyDiamondHistory(List<BuyDiamondHistoryResponse> buyDiamondHistory){
        if (buyDiamondHistory.size() == 0) {
            throw new ErrorException(ErrorType.NOT_FOUND_USER);
        }
    }
}
