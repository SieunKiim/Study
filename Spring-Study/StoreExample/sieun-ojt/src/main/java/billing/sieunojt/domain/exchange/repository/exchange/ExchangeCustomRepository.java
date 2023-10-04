package billing.sieunojt.domain.exchange.repository.exchange;

import billing.sieunojt.domain.exchange.model.dto.ExchangeDto;
import billing.sieunojt.domain.exchange.model.entity.Exchange;

import java.util.List;

public interface ExchangeCustomRepository {
    List<ExchangeDto.BuyDiamondHistoryResponse> findAllBuyExchanges(long userId);

    List<ExchangeDto.RefundDiamondHistoryResponse> findAllRefundExchanges(long userId);

    List<Exchange> findAvailableExchange(long userId);

    List<Exchange> findAllRefundableInId(List<Long> ids);
}
