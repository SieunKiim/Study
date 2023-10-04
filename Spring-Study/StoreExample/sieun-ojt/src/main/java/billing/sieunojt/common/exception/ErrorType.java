package billing.sieunojt.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@AllArgsConstructor
public enum ErrorType {
    // Internal Server Error 10000
    UNKNOWN_PG(HttpStatus.NOT_FOUND, 10000,"알 수 없는 PG사"),

    // User 50000 ~
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, 50001,"존재하지 않는 사용자"),

    // Exchange 51000 ~
    BUY_DIA_BAD_REQUEST(HttpStatus.BAD_REQUEST, 51000, "다이아 구매 시, 0 이하의 값 전달됨"),
    NOT_FOUND_EXCHANGE(HttpStatus.BAD_REQUEST, 51001, "환전 기록이 없음"),
    EXCHANGE_VALUE_INCONSISTENCY(HttpStatus.BAD_REQUEST, 51002, "환전 값이 일치하지 않은 요청"),
    EXCHANGE_REFUND_KAKAO_ISSUE(HttpStatus.INTERNAL_SERVER_ERROR, 51003, "카카오페이 환불 과정 이슈 발생"),
    NOT_ENOUGH_DIAMOND(HttpStatus.BAD_REQUEST, 51004, "잔여 다이아몬드보다 큰 환불 금액"),
    ALREADY_REFUNDED_EXCHANGE(HttpStatus.BAD_REQUEST, 51005, "이미 환불된 TID"),
    NOT_FOUND_EXCHANGE_ORDER(HttpStatus.BAD_REQUEST, 51006, "환전 주문 기록이 없음"),
    EXCHANGE_REFUND_INVALID(HttpStatus.BAD_REQUEST, 51007, "사용 전적이 있는 환전 정보"),
    ALREADY_APPROVED_EXCHANGE(HttpStatus.BAD_REQUEST, 51008, "이미 승인된 환전 요청"),
    BIGGER_THAN_ORIGINAL_DIAMOND(HttpStatus.BAD_REQUEST, 51009, "처음 구매한 다이아보다 잔여 다이아가 많음."),
    EXPIRED_EXCHANGE_CONTAINED(HttpStatus.BAD_REQUEST, 51010, "만료된 Exchange 포함됨."),
    OVER_LEFT_DIAMOND(HttpStatus.BAD_REQUEST, 51011, "잔여 다이아 보다 큰 요청 금액."),

    // Sale 52000 ~
    CANNOT_AFFORD_ITEM(HttpStatus.BAD_REQUEST, 52000, "잔액이 부족합니다."),
    NOT_FOUND_SALE(HttpStatus.BAD_REQUEST, 52001, "거래 내역이 없습니다."),
    NOT_REFUNDABLE_SALE(HttpStatus.BAD_REQUEST, 52002, "만료 기간으로 인한 반환 불가."),
    DUPLICATED_REFUND(HttpStatus.BAD_REQUEST, 52002, "중복 환불 불가."),

    // Item 53000 ~
    NOT_FOUND_ITEM(HttpStatus.BAD_REQUEST, 53000, "존재하지 않는 아이템"),
    ALREADY_REFUNDED_ITEM(HttpStatus.BAD_REQUEST, 53001, "이미 반품된 아이템"),
    BAD_REFUND_REQUEST(HttpStatus.BAD_REQUEST, 53002, "사용자가 구매한 내역이 아님"),

    // Spend 54000 ~
    NOT_FOUND_SPEND(HttpStatus.BAD_REQUEST, 54000, "다이아 사용 정보가 없음"),
    ;

    private final HttpStatus httpStatus;
    private final int errorCode;
    private final String message;

}
