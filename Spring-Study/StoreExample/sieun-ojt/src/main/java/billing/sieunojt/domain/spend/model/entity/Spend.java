package billing.sieunojt.domain.spend.model.entity;

import billing.sieunojt.common.entity.BaseTimeEntity;
import billing.sieunojt.common.util.LongArrayConverter;
import billing.sieunojt.domain.exchange.model.entity.Exchange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class Spend extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long exchangeId;

    private Long saleId;

    private Integer useAmount;
    @Builder
    public Spend(Long exchangeId, Long saleId, Integer useAmount) {
        this.exchangeId = exchangeId;
        this.saleId = saleId;
        this.useAmount = useAmount;
    }

    public static Spend from(Exchange exchange, Integer useAmount, Long saleId) {
        return Spend.builder()
                .saleId(saleId)
                .exchangeId(exchange.getId())
                .useAmount(useAmount)
                .build();
    }

    public void autoIncreaseId(Long idValue){
        this.id = idValue;
    }

}
