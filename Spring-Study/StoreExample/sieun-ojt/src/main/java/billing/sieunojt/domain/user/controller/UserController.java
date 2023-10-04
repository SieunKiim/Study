package billing.sieunojt.domain.user.controller;

import billing.sieunojt.common.dto.ResponseDto;
import billing.sieunojt.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{user_id}/diamond-balance")
    public ResponseEntity<?> getUserDiamondBalance(
            @PathVariable Long user_id
    ){
        var response = userService.getUserDiamondBalance(user_id);
        return ResponseDto.ok(response);
    }

    @GetMapping("/{user_id}/diamond-balance/renew")
    public ResponseEntity<?> renewUserDiamondBalanceCache(
            @PathVariable Long user_id
    ){
        var response = userService.renewCache(user_id);
        return ResponseDto.ok(response);
    }

}
