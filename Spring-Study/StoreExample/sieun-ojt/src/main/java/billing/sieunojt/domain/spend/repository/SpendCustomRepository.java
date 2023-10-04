package billing.sieunojt.domain.spend.repository;

import billing.sieunojt.domain.spend.model.dto.SpendDto.SpendExchangeDto;
import java.util.List;

public interface SpendCustomRepository {
    List<SpendExchangeDto> findAllUnExpiredSpendExchangeInfo(long saleId);
}
