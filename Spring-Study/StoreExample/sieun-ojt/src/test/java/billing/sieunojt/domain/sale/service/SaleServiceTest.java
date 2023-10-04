package billing.sieunojt.domain.sale.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.exchange.service.ExchangeService;
import billing.sieunojt.domain.sale.model.dto.SaleDto;
import billing.sieunojt.domain.sale.model.dto.SaleDto.BuyItemRequest;
import billing.sieunojt.domain.sale.model.dto.SaleDto.BuyItemResponse;
import billing.sieunojt.domain.sale.model.dto.SaleDto.RefundItemRequest;
import billing.sieunojt.domain.sale.model.entity.Sale;
import billing.sieunojt.domain.spend.model.entity.Spend;
import billing.sieunojt.domain.spend.service.SpendService;
import billing.sieunojt.domain.user.model.entity.User;
import billing.sieunojt.domain.user.service.UserService;
import billing.sieunojt.fake.construct.FakeConstructor;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SaleServiceTest {

    private final FakeConstructor fakeConstructor = new FakeConstructor();
    private final UserService userService = fakeConstructor.getUserService();
    private final SaleService saleService = fakeConstructor.getSaleService();
    private final ExchangeService exchangeService = fakeConstructor.getExchangeService();
    private final SpendService spendService = fakeConstructor.getSpendService();

    @BeforeEach
    void init() {
        userService.saveUser(new User(5000));
        exchangeService.save(new Exchange("a", 1L, 500, 500, false, 0, 500, 0L, LocalDateTime.MAX));
        exchangeService.save(new Exchange("b", 1L, 400, 400, false, 0, 400, 0L, LocalDateTime.MAX));
        exchangeService.save(new Exchange("c", 1L, 600, 600, false, 0, 600, 0L, LocalDateTime.MAX));
        exchangeService.save(new Exchange("a", 1L, 500, 500, false, 0, 500, 0L, LocalDateTime.MAX));
        exchangeService.save(new Exchange("c", 1L, 2000, 2000, false, 0, 2000, 0L, LocalDateTime.MAX));
        exchangeService.save(new Exchange("c", 1L, 1000, 1000, false, 0, 1000, 0L, LocalDateTime.MAX));
    }

    /**
     * [유저 정보]
     *  {
     *      id : 1L,
     *      balance : 5000
     *  }
     * [아이템 정보]
     *  {
     *      id : 1L,
     *      cost : 100,
     *      name : 일번아이템
     *  }
     * <p>
     * 구매 수량 : 10개
     */
    @Test
    void buyItemVer3() {
        // given
        BuyItemRequest requestStub = new BuyItemRequest(1L, 1L, 30);
        Integer beforeSaleUserDiamondBalance = userService.getUser(requestStub.getUser_id()).getDiamondBalance();


        // when
        BuyItemResponse result = saleService.buyItemVer3(requestStub);


        // then
        Sale resultSale = saleService.findById(result.getSale_id());
        Integer afterSaleUserDiamondBalance = userService.getUser(result.getUser_id()).getDiamondBalance();

        List<Spend> allSpendBySaleId = spendService.findAllSpendBySaleId(resultSale.getId());

        List<Long> usedExchangeId = allSpendBySaleId.stream().map(Spend::getExchangeId).toList();
        List<Exchange> usedExchanges = exchangeService.getAllInExchangeIds(usedExchangeId);
        assertAll(
                () -> assertThat(beforeSaleUserDiamondBalance - afterSaleUserDiamondBalance).isEqualTo(3000), // 사용자 잔액 차감 반영 확인
                () -> assertThat(allSpendBySaleId.size()).isEqualTo(5), // exchange 들의 잔여 다이아 반영 확인
                () -> assertThat(checkUsedExchanges(usedExchanges)).isEqualTo(true), // 사용한 exchange row 만큼 spend 생성되었는지 확인
                () -> assertThat(resultSale).isNotNull() // sale 발생했는지 확인 (반환된 saleId 값으로 조회 시, null 여부 판별)
        );
    }

    @Test
    void refundItem() {
        //given
        // SPEND랑 sale이 미리 저장되어있어야함.


        RefundItemRequest request = new RefundItemRequest(1L, 1L);

        //when

        //then
    }

    private boolean checkUsedExchanges(List<Exchange> exchanges) {
        for (int i = 0; i < exchanges.size() - 1; i++) {
            Exchange exchange = exchanges.get(i);
            if (exchange.getLeftDiamond() != 0) {
                return false;
            }
        }
        return true;
    }
}