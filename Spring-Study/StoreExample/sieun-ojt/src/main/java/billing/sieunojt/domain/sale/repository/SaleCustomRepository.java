package billing.sieunojt.domain.sale.repository;

import billing.sieunojt.domain.sale.model.dto.SaleDto;
import billing.sieunojt.domain.sale.model.dto.SaleDto.BuyItemInfo;
import billing.sieunojt.domain.sale.model.dto.SaleDto.SaleItemInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SaleCustomRepository {
    Page<SaleDto.BuyHistoryResponse> getBuyHistory(long userId, Pageable pageable);
    Page<SaleDto.RefundHistoryResponse> getRefundHistory(long userId, Pageable pageable);

    // 근데 이게 맞는걸까? (Sale을 만들기 위해 필요한 User정보와 Item 정보를 가져옴) - Sale테이블은 조회 조차 하지 않는데 UserRepository에 만들기도, ItemRepository에 만들기도 애매해...
    BuyItemInfo getSaleRequires(long userId, long itemId);

    SaleItemInfo getSaleItemInfo(Long saleId);
}
