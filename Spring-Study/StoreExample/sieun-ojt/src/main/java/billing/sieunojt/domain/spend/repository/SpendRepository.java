package billing.sieunojt.domain.spend.repository;

import billing.sieunojt.domain.spend.model.entity.Spend;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpendRepository extends JpaRepository<Spend, Long>, SpendCustomRepository{

    List<Spend> findAllBySaleId(Long saleId);
}
