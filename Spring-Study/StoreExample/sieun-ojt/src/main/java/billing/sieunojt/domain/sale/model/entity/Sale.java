package billing.sieunojt.domain.sale.model.entity;

import billing.sieunojt.common.entity.BaseTimeEntity;
import billing.sieunojt.domain.sale.model.dto.SaleDto.SaleItemInfo;
import billing.sieunojt.domain.sale.model.dto.SaleDto.SaleRequires;
import billing.sieunojt.domain.spend.model.dto.SpendDto;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Sale extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private long itemId;

    private int totalCost;

    private int amount;

    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean isRefund;

    private long targetSaleId;

    private LocalDateTime expiredAt;

    @Builder
    public Sale(long userId, long itemId, int totalCost, int amount, boolean isRefund,
            long targetSaleId, LocalDateTime expiredAt) {
        this.userId = userId;
        this.itemId = itemId;
        this.totalCost = totalCost;
        this.amount = amount;
        this.isRefund = isRefund;
        this.targetSaleId = targetSaleId;
        this.expiredAt = expiredAt;
    }

    public static Sale createBuySale(SaleRequires saleRequires) {
        return Sale.builder()
                .userId(saleRequires.getUserId())
                .itemId(saleRequires.getItemId())
                .amount(saleRequires.getAmount())
                .totalCost(saleRequires.getTotalCost())
                .isRefund(false)
                .expiredAt(LocalDateTime.now().plusDays(7L))
                .build();
    }

    public static Sale createRefundSale(SaleItemInfo spendInfoDao) {
        return Sale.builder()
                .userId(spendInfoDao.getUserId())
                .itemId(spendInfoDao.getItemId())
                .totalCost(spendInfoDao.getTotalCost())
                .amount(spendInfoDao.getAmount())
                .isRefund(true)
                .targetSaleId(spendInfoDao.getSaleId())
                .build();
    }

    public static Integer calculateTotalCost(Integer itemCost, Integer amount) {
        return itemCost * amount;
    }


    public void autoIncreaseId(Long idValue){
        this.id = idValue;
    }
}
