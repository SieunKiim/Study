package billing.sieunojt.domain.user.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

    @Getter
    @NoArgsConstructor
    public static class GetUserDiamondBalanceResponse {
        private Long user_id;
        private Integer diamond_balance;

        public GetUserDiamondBalanceResponse(Long userId, Integer userDiamond) {
            this.user_id = userId;
            this.diamond_balance = userDiamond;
        }
    }
}
