package billing.sieunojt.domain.exchange.model.entity;

import billing.sieunojt.common.entity.BaseTimeEntity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeOrder extends BaseTimeEntity {
    @Id
    private String partnerOrderId;

    private String partnerUserId;

    private long userId;

    private int cash;

    private int diamond;

    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean isRefund;

    private String tid;

    private String successCode; // 결제 승인 코드

    private String PgProvider; // pg사 이름

    @Builder
    public ExchangeOrder(String partnerOrderId, String partnerUserId, long userId, int cash, int diamond, boolean isRefund, String tid, String successCode, String pgProvider) {
        this.partnerOrderId = partnerOrderId;
        this.partnerUserId = partnerUserId;
        this.userId = userId;
        this.cash = cash;
        this.diamond = diamond;
        this.isRefund = isRefund;
        this.tid = tid;
        this.successCode = successCode;
        this.PgProvider = pgProvider;
    }

    public void payApproval(String successCode, String tid) {
        this.successCode = successCode;
        this.tid = tid;
    }

    public void payApproval(String successCode) {
        this.successCode = successCode;
    }

}
