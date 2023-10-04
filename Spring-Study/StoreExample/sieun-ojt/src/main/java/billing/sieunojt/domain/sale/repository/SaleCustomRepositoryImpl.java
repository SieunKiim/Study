package billing.sieunojt.domain.sale.repository;

import static billing.sieunojt.domain.item.model.entity.QItem.item;
import static billing.sieunojt.domain.sale.model.entity.QSale.sale;
import static billing.sieunojt.domain.user.model.entity.QUser.user;

import billing.sieunojt.domain.sale.model.dto.QSaleDto_BuyHistoryResponse;
import billing.sieunojt.domain.sale.model.dto.QSaleDto_RefundHistoryResponse;
import billing.sieunojt.domain.sale.model.dto.SaleDto;
import billing.sieunojt.domain.sale.model.dto.SaleDto.BuyItemInfo;
import billing.sieunojt.domain.sale.model.dto.SaleDto.SaleItemInfo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class SaleCustomRepositoryImpl implements SaleCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<SaleDto.BuyHistoryResponse> getBuyHistory(long userId, Pageable pageable) {
        List<SaleDto.BuyHistoryResponse> histories = jpaQueryFactory
                .select(new QSaleDto_BuyHistoryResponse(sale.id, sale.userId, item.id, item.name,
                        item.diamond, sale.amount, sale.totalCost))
                .from(sale, item)
                .where(sale.itemId.eq(item.id).and(sale.isRefund.eq(false))
                        .and(sale.userId.eq(userId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = jpaQueryFactory
                .select(sale)
                .from(sale)
                .where(sale.isRefund.eq(false).and(sale.userId.eq(userId)))
                .fetch().size();
        return new PageImpl<>(histories, pageable, count);
    }

    @Override
    public Page<SaleDto.RefundHistoryResponse> getRefundHistory(long userId, Pageable pageable) {
        List<SaleDto.RefundHistoryResponse> histories = jpaQueryFactory
                .select(new QSaleDto_RefundHistoryResponse(sale.id, sale.userId, item.id, item.name,
                        item.diamond, sale.amount, sale.totalCost))
                .from(sale, item)
                .where(sale.itemId.eq(item.id).and(sale.isRefund.eq(true))
                        .and(sale.userId.eq(userId)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long count = jpaQueryFactory
                .select(sale)
                .from(sale)
                .where(sale.isRefund.eq(true).and(sale.userId.eq(userId)))
                .fetch().size();
        return new PageImpl<>(histories, pageable, count);
    }

    @Override
    public BuyItemInfo getSaleRequires(long userId, long itemId) {
        return jpaQueryFactory.select(Projections.fields(BuyItemInfo.class,
                        user.id.as("userId"),
                        item.id.as("itemId"),
                        user.diamondBalance.as("userDiamondBalance"),
                        item.diamond.as("itemCost"),
                        item.name.as("itemName")
                ))
                .from(user, item)
                .where(user.id.eq(userId), item.id.eq(itemId))
                .fetchOne();
    }

    @Override
    public SaleItemInfo getSaleItemInfo(Long saleId) {
        return jpaQueryFactory.select(Projections.fields(SaleItemInfo.class,
                        sale.id.as("saleId"),
                        sale.amount.as("amount"),
                        sale.totalCost.as("totalCost"),
                        item.id.as("itemId"),
                        item.name.as("itemName"),
                        item.diamond.as("itemCost"),
                        sale.userId.as("userId")
                ))
                .from(sale, item)
                .where(
                        sale.itemId.eq(item.id),
                        sale.id.eq(saleId),
                        sale.isRefund.eq(false),
                        sale.expiredAt.after(LocalDateTime.now())
                ).fetchOne();
    }
}
