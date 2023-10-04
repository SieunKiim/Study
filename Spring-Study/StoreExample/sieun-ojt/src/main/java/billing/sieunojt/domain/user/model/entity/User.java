package billing.sieunojt.domain.user.model.entity;


import billing.sieunojt.common.entity.BaseTimeEntity;
import billing.sieunojt.common.exception.ErrorException;
import billing.sieunojt.common.exception.ErrorType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cache.annotation.CachePut;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer diamondBalance;

    public User(Integer diamondBalance) {
        this.diamondBalance = diamondBalance;
    }

    public Integer withdraw(Integer amount){
        if (this.diamondBalance - amount < 0) {
            throw new ErrorException(ErrorType.NOT_ENOUGH_DIAMOND);
        }
        this.diamondBalance -= amount;
        return this.diamondBalance;
    }

    public Integer deposit(int amount) {
        this.diamondBalance += amount;
        return this.diamondBalance;
    }

    public void autoIncreaseId(Long idValue){
        this.id = idValue;
    }
}
