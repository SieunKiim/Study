package billing.sieunojt.common.exception;

import lombok.Getter;

@Getter
public class ErrorException extends RuntimeException{
    private final ErrorType errorType;
    public ErrorException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorException(ErrorType errorType, String customMessage) {
        super(customMessage);
        this.errorType = errorType;
    }
}
