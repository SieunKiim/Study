package billing.sieunojt.domain.sale.service;

import billing.sieunojt.common.exception.ErrorException;
import billing.sieunojt.common.exception.ErrorType;
import billing.sieunojt.domain.exchange.model.collection.AvailableExchanges;
import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.exchange.service.ExchangeService;
import billing.sieunojt.domain.item.service.ItemService;
import billing.sieunojt.domain.sale.model.dto.SaleDto;
import billing.sieunojt.domain.sale.model.dto.SaleDto.BuyItemInfo;
import billing.sieunojt.domain.sale.model.dto.SaleDto.BuyItemRequest;
import billing.sieunojt.domain.sale.model.dto.SaleDto.BuyItemResponse;
import billing.sieunojt.domain.sale.model.dto.SaleDto.RefundItemResponse;
import billing.sieunojt.domain.sale.model.dto.SaleDto.SaleItemInfo;
import billing.sieunojt.domain.sale.model.dto.SaleDto.SaleRequires;
import billing.sieunojt.domain.sale.model.entity.Sale;
import billing.sieunojt.domain.sale.repository.SaleRepository;
import billing.sieunojt.domain.spend.model.collection.SpendExchangeInfos;
import billing.sieunojt.domain.spend.service.SpendService;
import billing.sieunojt.domain.user.service.UserService;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ExchangeService exchangeService;
    private final UserService userService;
    private final SpendService spendService;

    @Transactional
    public SaleDto.BuyItemResponse buyItemVer3(SaleDto.BuyItemRequest request) {
        SaleRequires saleRequires = prepareSaleRequires(request);
        userService.checkAffordable(saleRequires.getUserId(), saleRequires.getTotalCost());
        Integer leftBalance = userService.withdrawDiamond(saleRequires.getUserId(),
                saleRequires.getTotalCost());
        AvailableExchanges availableExchanges = exchangeService.findAvailableExchange(
                saleRequires.getUserId());
        // sale 생성
        Sale savedSale = saleRepository.save(Sale.createBuySale(saleRequires));
        // spend 처리
        spendService.spendDiamondByBuyItem(availableExchanges, saleRequires, savedSale.getId());
        return new BuyItemResponse(savedSale, saleRequires.getItemName(), leftBalance);
    }

    @Transactional
    public RefundItemResponse refundItem(SaleDto.RefundItemRequest request){
        checkRefundedSale(request.getSale_id());
        SaleItemInfo saleIteminfo = getSaleIteminfo(request.getSale_id());
        SpendExchangeInfos spendExchangeInfos = spendService.getSpendExchangeInfos(
                request.getSale_id());
        spendService.revertUsedSpend(saleIteminfo, spendExchangeInfos);
        saleRepository.save(Sale.createRefundSale(saleIteminfo));
        userService.depositDiamond(saleIteminfo.getUserId(), saleIteminfo.getTotalCost());
        return RefundItemResponse.from(saleIteminfo);
    }

    public Page<SaleDto.BuyHistoryResponse> buyHistory(long userId, Pageable pageable) {
        return saleRepository.getBuyHistory(userId, pageable);
    }

    public Page<SaleDto.RefundHistoryResponse> refundHistory(long userId, Pageable pageable) {
        return saleRepository.getRefundHistory(userId, pageable);
    }

    private SaleRequires prepareSaleRequires(BuyItemRequest request) {
        BuyItemInfo buyItemInfo = saleRepository.getSaleRequires(request.getUser_id(),
                request.getItem_id());
        return new SaleRequires(buyItemInfo, request.getAmount());
    }

    public void checkRefundedSale(Long targetSaleId) {
        if (saleRepository.existsByTargetSaleId(targetSaleId)){
            throw new ErrorException(ErrorType.DUPLICATED_REFUND);
        }
    }

    public SaleItemInfo getSaleIteminfo(Long saleId) {
        return saleRepository.getSaleItemInfo(saleId);
    }

    public Sale findById(Long saleId) {
        return saleRepository.findById(saleId)
                .orElseThrow(() -> new ErrorException(ErrorType.NOT_FOUND_SALE));
    }

}
