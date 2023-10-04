package billing.sieunojt.common.exception;

import billing.sieunojt.common.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ErrorException.class)
    public ResponseEntity<ResponseDto<Void>> exceptionHandler(ErrorException e) {
        ErrorType errorType = e.getErrorType();
        return new ResponseEntity<>(new ResponseDto<>(errorType), errorType.getHttpStatus());
    }
}
