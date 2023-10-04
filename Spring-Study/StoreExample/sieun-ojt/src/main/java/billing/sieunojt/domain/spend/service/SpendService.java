package billing.sieunojt.domain.spend.service;

import billing.sieunojt.common.exception.ErrorException;
import billing.sieunojt.common.exception.ErrorType;
import billing.sieunojt.domain.exchange.model.collection.AvailableExchanges;
import billing.sieunojt.domain.exchange.model.collection.UsingExchanges;
import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.exchange.service.ExchangeService;
import billing.sieunojt.domain.sale.model.dto.SaleDto.SaleItemInfo;
import billing.sieunojt.domain.sale.model.dto.SaleDto.SaleRequires;
import billing.sieunojt.domain.spend.model.collection.SpendExchangeInfos;
import billing.sieunojt.domain.spend.model.entity.Spend;
import billing.sieunojt.domain.spend.repository.SpendRepository;
import billing.sieunojt.domain.user.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpendService {

    private final SpendRepository spendRepository;
    private final ExchangeService exchangeService;

    @Transactional
    public Spend saveSpend(Spend spend) {
        return spendRepository.save(spend);
    }

    @Transactional
    public void spendDiamondByBuyItem(AvailableExchanges availableExchanges,
            SaleRequires saleRequires, Long saleId) {
        UsingExchanges usingExchanges = availableExchanges.getUsingExchanges(
                saleRequires.getTotalCost());

        spendProcessExceptLast(usingExchanges, saleId);
        spendProcessLast(usingExchanges, saleRequires, saleId);
    }

    @Transactional(readOnly = true)
    public SpendExchangeInfos getSpendExchangeInfos(Long saleId) {
        return new SpendExchangeInfos(spendRepository.findAllUnExpiredSpendExchangeInfo(saleId));
    }

    @Transactional
    public void spendProcessExceptLast(UsingExchanges exchanges, Long saleId) {
        for (Exchange ex : exchanges.getExchangesExceptLast()) {
            int useAmount = ex.getLeftDiamond();
            ex.useLeftDiamond(useAmount);
            Spend spend = Spend.from(ex, useAmount, saleId);
            saveSpend(spend);
        }
    }

    @Transactional
    public void spendProcessLast(UsingExchanges exchanges, SaleRequires saleRequires, Long saleId) {
        Exchange lastExchange = exchanges.getLastExchange();
        int useAmount = exchanges.calculateLastExchangeUsage(saleRequires.getTotalCost());
        lastExchange.useLeftDiamond(useAmount);
        Spend spend = Spend.from(lastExchange, useAmount, saleId);
        saveSpend(spend);
    }

    @Transactional
    public void revertUsedSpend(SaleItemInfo saleItemInfo, SpendExchangeInfos spendExchangeInfos) {
        checkEveryExchangesAvailable(saleItemInfo, spendExchangeInfos);
        exchangeService.revertExchangeUsage(spendExchangeInfos);
    }

    @Transactional(readOnly = true)
    public List<Spend> findAllSpendBySaleId(Long saleId) {
        return spendRepository.findAllBySaleId(saleId);
    }

    private void checkEveryExchangesAvailable(SaleItemInfo saleItemInfo, SpendExchangeInfos spendExchangeInfos){
        if (!saleItemInfo.getTotalCost().equals(spendExchangeInfos.getTotalSpendSum())) {
            throw new ErrorException(ErrorType.EXPIRED_EXCHANGE_CONTAINED);
        }
    }
}
