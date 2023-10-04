package billing.sieunojt.domain.exchange.repository.exchange;

import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.BuyDiamondHistoryResponse;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.RefundDiamondHistoryResponse;
import billing.sieunojt.domain.exchange.model.entity.Exchange;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static billing.sieunojt.domain.exchange.model.entity.QExchange.exchange;

@Repository
@RequiredArgsConstructor
public class ExchangeCustomRepositoryImpl implements ExchangeCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<BuyDiamondHistoryResponse> findAllBuyExchanges(long userId){
        return jpaQueryFactory.select(Projections.fields(
                BuyDiamondHistoryResponse.class,
                        exchange.cash,
                        exchange.diamond,
                        exchange.createdAt.as("date")
                        )
                )
                .where(exchange.userId.eq(userId), exchange.isRefund.eq(false))
                .from(exchange)
                .orderBy(exchange.createdAt.desc())
                .fetch();
    }

    @Override
    public List<RefundDiamondHistoryResponse> findAllRefundExchanges(long userId) {
        return jpaQueryFactory.select(Projections.fields(
                RefundDiamondHistoryResponse.class,
                        exchange.cash,
                        exchange.diamond,
                        exchange.createdAt.as("date")
                        )
                )
                .from(exchange)
                .where(exchange.userId.eq(userId), exchange.isRefund.eq(true))
                .orderBy(exchange.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Exchange> findAvailableExchange(long userId) {
        return jpaQueryFactory.select(exchange)
                .from(exchange)
                .where(
                        exchange.isRefund.eq(false),
                        exchange.expiredAt.after(LocalDateTime.now()),
                        exchange.leftDiamond.gt(0)
                )
                .orderBy(exchange.createdAt.asc())
                .fetch();
    }

    @Override
    public List<Exchange> findAllRefundableInId(List<Long> ids) {
        return jpaQueryFactory.select(exchange)
                .from(exchange)
                .where(exchange.expiredAt.after(LocalDateTime.now()), exchange.id.in(ids))
                .orderBy(exchange.createdAt.asc())
                .fetch();
    }
}
