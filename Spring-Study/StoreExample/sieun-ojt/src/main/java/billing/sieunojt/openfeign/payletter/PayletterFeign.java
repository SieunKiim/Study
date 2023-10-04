package billing.sieunojt.openfeign.payletter;


import billing.sieunojt.common.config.openFeign.PayletterFeignConfig;
import billing.sieunojt.openfeign.payletter.dto.PayletterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "PayletterFeign", url = "https://testpgapi.payletter.com", configuration = {PayletterFeignConfig.class})
public interface PayletterFeign {
    @PostMapping(value = "${pg.payletter.request}", consumes = MediaType.APPLICATION_JSON_VALUE)
    PayletterDto.PrepareResponseDto preparePay(PayletterDto.PrepareRequestDto request);

    @PostMapping(value = "${pg.payletter.cancel}", consumes = MediaType.APPLICATION_JSON_VALUE)
    PayletterDto.RefundResponseDto refund(PayletterDto.RefundRequestDto request);
}
