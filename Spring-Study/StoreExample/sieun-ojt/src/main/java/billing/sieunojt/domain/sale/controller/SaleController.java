package billing.sieunojt.domain.sale.controller;

import billing.sieunojt.common.dto.ResponseDto;
import billing.sieunojt.domain.sale.model.dto.SaleDto;
import billing.sieunojt.domain.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService saleService;

    @GetMapping("/buy")
    public ResponseEntity<?> getBuyHistory(
            @RequestParam long user_id,
            Pageable pageable
    ) {
        var response = saleService.buyHistory(user_id, pageable);
        return ResponseDto.ok(response);
    }

    @PostMapping("/buy")
    public ResponseEntity<?> buyItem(
            @RequestBody SaleDto.BuyItemRequest request
    ) {
        var response = saleService.buyItemVer3(request);
        return ResponseDto.ok(response);
    }

    @GetMapping("/refund")
    public ResponseEntity<?> getRefundHistory(
            @RequestParam long user_id,
            Pageable pageable
    ) {
        var response = saleService.refundHistory(user_id, pageable);
        return ResponseDto.ok(response);
    }

    @PostMapping("/refund")
    public ResponseEntity<?> refundItem(
            @RequestBody SaleDto.RefundItemRequest request
    ) {
        var response = saleService.refundItem(request);
        return ResponseDto.ok(response);
    }
}
