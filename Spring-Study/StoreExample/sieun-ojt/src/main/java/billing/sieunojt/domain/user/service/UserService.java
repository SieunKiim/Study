package billing.sieunojt.domain.user.service;

import billing.sieunojt.common.exception.ErrorException;
import billing.sieunojt.common.exception.ErrorType;
import billing.sieunojt.domain.user.model.dto.UserDto.GetUserDiamondBalanceResponse;
import billing.sieunojt.domain.user.model.entity.User;
import billing.sieunojt.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorType.NOT_FOUND_USER));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void isExistUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ErrorException(ErrorType.NOT_FOUND_USER);
        }
    }

    @Transactional
    @CachePut(key = "#userId", value = "getUserDiamondBalance")
    public Integer depositDiamond(Long userId, Integer amount) {
        return getUser(userId).deposit(amount);
    }

    @Transactional
    @CachePut(key = "#userId", value = "getUserDiamondBalance")
    public Integer withdrawDiamond(Long userId, Integer amount) {
        return getUser(userId).withdraw(amount);
    }

    @Transactional(readOnly = true)
    public GetUserDiamondBalanceResponse getUserDiamondBalance(Long userId) {
        Integer userDiamondBalance = getCachedUserBalance(userId);
        return new GetUserDiamondBalanceResponse(userId, userDiamondBalance);
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#userId", value = "getUserDiamondBalance")
    public Integer getCachedUserBalance(Long userId) {
        return getUser(userId).getDiamondBalance();
    }

    public void checkAffordable(Long userId, Integer totalCost) {
        User user = getUser(userId);
        if(user.getDiamondBalance() < totalCost){
            throw new ErrorException(ErrorType.CANNOT_AFFORD_ITEM);
        }
    }
    @Transactional
    @CachePut(key = "#userId", value = "getUserDiamondBalance")
    public Integer renewCache(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ErrorException(ErrorType.NOT_FOUND_USER));
        return user.getDiamondBalance();
    }

}
