package billing.sieunojt.domain.exchange.controller;

import billing.sieunojt.common.dto.ResponseDto;
import billing.sieunojt.domain.exchange.model.collection.BuyDiamondHistory;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.RefundDiamondRequest;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.BuyDiamondsApprovalResponse;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.BuyDiamondsRequest;
import billing.sieunojt.domain.exchange.service.ExchangeService;
import billing.sieunojt.domain.exchange.service.payment.type.PgType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/exchange")
public class ExchangeController {
    private final ExchangeService exchangeService;

    @PostMapping(value = "/payletter/test", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> returnUrlTest(
            ExchangeDto.PayletterReturnValueDto request
    ) {
        exchangeService.payletterInsteadCallback(request);
        return ResponseDto.ok(request);
    }

    @GetMapping("/buy")
    public ResponseEntity<ResponseDto<BuyDiamondHistory>> getBuyDiamondExchanges(
            @RequestParam Long user_id) {
        var response = exchangeService.getBuyDiamondExchanges(user_id);
        return ResponseDto.ok(response);
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyDiamonds(
            @RequestBody BuyDiamondsRequest request
    ) {
        var response = exchangeService.buyDiamondsPrepare(request);
        return ResponseDto.ok(response);
    }

    @GetMapping("/approve")
    public ResponseEntity<ResponseDto<BuyDiamondsApprovalResponse>> exchangeApproval(
            @RequestParam String pg_token,
            @RequestParam String order_id,
            @RequestParam(value = "pg") PgType pg
    ) {
        var response = exchangeService.buyDiamondApproval(pg, pg_token, order_id);
        return ResponseDto.ok(response);
    }


    @GetMapping("/refund")
    public ResponseEntity<?> refundDiamondsHistory(
            @RequestParam long user_id
    ){
        var response = exchangeService.getRefundDiamondExchanges(user_id);
        return ResponseDto.ok(response);
    }

    @PostMapping("/refund")
    public ResponseEntity<?> refundDiamonds(
            @RequestBody RefundDiamondRequest request,
            @RequestAttribute(value = "ip-address") String ip
    ) {
        var response = exchangeService.exchangeRefund(request, ip);
        return ResponseDto.ok(response);
    }
}
