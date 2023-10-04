package billing.sieunojt.domain.exchange.repository.exchange;

import billing.sieunojt.domain.exchange.model.entity.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long>, ExchangeCustomRepository {
    Optional<Exchange> findByPartnerOrderId(String id);
    Boolean existsByPartnerOrderIdAndIsRefund(String id, Boolean isRefund);
    List<Exchange> findAllByExpiredAtBeforeAndLeftDiamondNot(LocalDateTime date, int lef);

    List<Exchange> findAllByIdIn(List<Long> ids);
}
