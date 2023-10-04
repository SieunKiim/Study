package billing.sieunojt.domain.exchange.repository.exchangeOrder;

import billing.sieunojt.domain.exchange.model.entity.ExchangeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeOrderRepository extends JpaRepository<ExchangeOrder, Long> {
    Optional<ExchangeOrder> findByPartnerOrderId(String orderId);
}
