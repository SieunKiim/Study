package billing.sieunojt.domain.sale.repository;

import billing.sieunojt.domain.sale.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, Long> , SaleCustomRepository {
    Boolean existsByTargetSaleId(long saleId);
}
