package billing.sieunojt.domain.user.model.entity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import billing.sieunojt.domain.user.repository.UserRepository;
import billing.sieunojt.domain.user.service.UserService;
import billing.sieunojt.fake.construct.FakeConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    private final FakeConstructor fakeConstructor = new FakeConstructor();
    private final UserRepository userRepository = fakeConstructor.getUserRepository();
    private final UserService userService = fakeConstructor.getUserService();

    @Test
    @DisplayName("생성 테스트")
    void create(){
        assertDoesNotThrow(() -> new User(40));
    }

    @Test
    @DisplayName("다이아 사용 테스트")
    void withdraw() {
        //given
        User user = userService.saveUser(new User(1000));

        //when
        Integer userBalance = user.withdraw(600);

        //then
        assertThat(userBalance).isEqualTo(400);
    }

    @Test
    void deposit() {
        //given
        User user = userService.saveUser(new User(1000));

        //when
        Integer userBalance = user.deposit(700);

        //then
        assertThat(userBalance).isEqualTo(1700);
    }
}