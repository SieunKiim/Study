package billing.sieunojt.domain.exchange.model.entity;

import billing.sieunojt.common.entity.BaseTimeEntity;
import billing.sieunojt.common.exception.ErrorException;
import billing.sieunojt.common.exception.ErrorType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exchange extends BaseTimeEntity { // 환전 (현금을 <-> 다이아몬드)
    @Transient
    @Value("${exchange.rate.exchange}")
    private int EXCHANGE_RATE;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String partnerOrderId;

    private Long userId;

    private Integer cash;

    private Integer diamond;

//    @Setter
    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean isRefund;

    private Integer exchangeCharge; // 수수료

//    @Setter
    private Integer leftDiamond; // 해당 결제 내역 잔여 다이아

    private Long targetRefundId;

    private LocalDateTime expiredAt;
    @Builder
    public Exchange(String partnerOrderId, long userId, int cash, int diamond, boolean isRefund, int exchangeCharge, int leftDiamond, Long targetRefundId, LocalDateTime expiredAt) {
        this.partnerOrderId = partnerOrderId;
        this.userId = userId;
        this.cash = cash;
        this.diamond = diamond;
        this.isRefund = isRefund;
        this.exchangeCharge = exchangeCharge;
        this.leftDiamond = leftDiamond;
        this.targetRefundId = targetRefundId;
        this.expiredAt = expiredAt;
    }

    public static Exchange createBuyExchangeFromExchangeOrder(ExchangeOrder eo) {
        return Exchange.builder()
                .partnerOrderId(eo.getPartnerOrderId())
                .userId(eo.getUserId())
                .cash(eo.getCash())
                .diamond(eo.getDiamond())
                .isRefund(false)
                .exchangeCharge(0)
                .leftDiamond(eo.getDiamond())
                .targetRefundId(0L)
                .expiredAt(LocalDateTime.now().plusDays(5))
                .build();
    }

    public Integer useLeftDiamond(Integer diamondUsage) {
        if (!checkUseAbleExchange(diamondUsage)) {
            throw new ErrorException(ErrorType.OVER_LEFT_DIAMOND);
        }
        this.leftDiamond -= diamondUsage;
        return this.leftDiamond;
    }

    public Integer useAllLeftDiamond() {
        this.leftDiamond = 0;
        return this.leftDiamond;
    }

    private Boolean checkUseAbleExchange(Integer diamondUsage) {
        return this.leftDiamond >= diamondUsage;
    }

    public Integer recoverUsedLeftDiamond(Integer diamondUsage){
        if (!checkRefundableExchange(diamondUsage)) {
            throw new ErrorException(ErrorType.BIGGER_THAN_ORIGINAL_DIAMOND);
        }
        this.leftDiamond += diamondUsage;
        return this.leftDiamond;
    }

    public Boolean checkRefundableExchange(Integer diamondUsage) {
        return this.diamond >= this.leftDiamond + diamondUsage;
    }


    public void autoIncreaseId(Long idValue){
        this.id = idValue;
    }
}
