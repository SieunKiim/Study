package billing.sieunojt.domain.item.model.entity;

import billing.sieunojt.common.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Item extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int diamond;

    public Item(long id, String name, int diamond) {
        this.id = id;
        this.name = name;
        this.diamond = diamond;
    }
}
