package billing.sieunojt.common.dto;

import billing.sieunojt.common.exception.ErrorType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@AllArgsConstructor
public class ResponseDto<T> {

    private int code;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T value;

    public ResponseDto(ErrorType errorType) {
        this.code = errorType.getErrorCode();
        this.message = errorType.getMessage();
    }

    public static <T> ResponseEntity<ResponseDto<T>> okCustomMessage(String message, T value){
        return new ResponseEntity<>(new ResponseDto<>(0, message, value), HttpStatus.OK);
    }

    public static <T> ResponseEntity<ResponseDto<T>> ok(T value){
        return new ResponseEntity<>(new ResponseDto<>(0, "success", value), HttpStatus.OK);
    }
}
