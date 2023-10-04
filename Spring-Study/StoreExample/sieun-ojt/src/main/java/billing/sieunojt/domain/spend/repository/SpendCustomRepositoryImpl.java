package billing.sieunojt.domain.spend.repository;

import static billing.sieunojt.domain.exchange.model.entity.QExchange.exchange;
import static billing.sieunojt.domain.spend.model.entity.QSpend.spend;

import billing.sieunojt.domain.spend.model.dto.SpendDto.SpendExchangeDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SpendCustomRepositoryImpl implements SpendCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<SpendExchangeDto> findAllUnExpiredSpendExchangeInfo(long saleId) {
        return jpaQueryFactory.select(Projections.fields(SpendExchangeDto.class,
                exchange.as("exchange"),
                spend.as("spend")
                        ))
                .from(spend, exchange)
                .where(
                        spend.exchangeId.eq(exchange.id),
                        spend.saleId.eq(saleId),
                        exchange.isRefund.eq(false),
                        exchange.expiredAt.after(LocalDateTime.now())
                )
                .fetch();
    }

//    @Override
//    public List<SpendDao.SpendInfoDao> getSpendInfo(long saleId) {
//        return jpaQueryFactory.select(Projections.fields(SpendDao.SpendInfoDao.class,
//                                sale.userId,
//                                spend.saleId,
//                                spend.exchangeId,
//                                sale.itemId,
//                                item.name,
//                                sale.amount,
//                                spend.useAmount,
//                                sale.totalCost
//                        )
//                )
//                .from(spend, sale, item)
//                .where(
//                        spend.saleId.eq(saleId),
//                        spend.saleId.eq(sale.id),
//                        sale.expiredAt.after(LocalDateTime.now()),
//                        sale.isRefund.eq(false),
//                        sale.targetSaleId.eq(0L),
//                        sale.itemId.eq(item.id),
//                        sale.targetSaleId.notIn(saleId)
//                )
//                .fetch();
//    }
}
