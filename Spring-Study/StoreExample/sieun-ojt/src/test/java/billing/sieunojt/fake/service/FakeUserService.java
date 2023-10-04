package billing.sieunojt.fake.service;

import billing.sieunojt.domain.user.repository.UserRepository;
import billing.sieunojt.domain.user.service.UserService;

public class FakeUserService extends UserService {


    public FakeUserService(UserRepository userRepository) {
        super(userRepository);
    }

}
