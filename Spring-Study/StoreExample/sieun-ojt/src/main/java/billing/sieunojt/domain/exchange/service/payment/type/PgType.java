package billing.sieunojt.domain.exchange.service.payment.type;

import billing.sieunojt.common.exception.ErrorException;
import billing.sieunojt.common.exception.ErrorType;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PgType {
    KAKAOPAY("kakaopay"),
    PAYLETTER("payletter")
    ;

    private final String pgName;

    @JsonCreator
    public static PgType parsing(String pg){
        return Stream.of(PgType.values())
                .filter(pgType -> pgType.getPgName().equals(pg))
                .findFirst()
                .orElseThrow(() -> new ErrorException(ErrorType.UNKNOWN_PG));
    }
}
