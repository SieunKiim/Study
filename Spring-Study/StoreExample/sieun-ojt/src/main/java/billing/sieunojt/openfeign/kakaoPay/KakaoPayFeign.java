package billing.sieunojt.openfeign.kakaoPay;

import billing.sieunojt.common.config.openFeign.KakaoPayFeignConfig;
import billing.sieunojt.openfeign.kakaoPay.dto.KakaoPayDto.PrepareRequestDto;
import billing.sieunojt.openfeign.kakaoPay.dto.KakaoPayDto.ApprovalRequestDto;
import billing.sieunojt.openfeign.kakaoPay.dto.KakaoPayDto.RefundRequestDto;
import billing.sieunojt.openfeign.kakaoPay.dto.KakaoPayDto.RefundResponseDto;
import billing.sieunojt.openfeign.kakaoPay.dto.KakaoPayDto.ApprovalResponseDto;
import billing.sieunojt.openfeign.kakaoPay.dto.KakaoPayDto.PrepareResponseDto;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "KakaoPayFeign", url = "https://kapi.kakao.com", configuration = {KakaoPayFeignConfig.class})
public interface KakaoPayFeign {
    @PostMapping(value = "${pg.kakao-pay.prepare}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    PrepareResponseDto preparePay(
            PrepareRequestDto request
    );

    @PostMapping(value = "${pg.kakao-pay.approve}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ApprovalResponseDto approvalPay(
            ApprovalRequestDto requestDto
    );

    @PostMapping(value = "${pg.kakao-pay.cancel}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    RefundResponseDto refund(
            RefundRequestDto request
    );
}
