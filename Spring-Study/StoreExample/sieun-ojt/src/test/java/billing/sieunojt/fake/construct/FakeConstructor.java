package billing.sieunojt.fake.construct;

import billing.sieunojt.domain.exchange.repository.exchange.ExchangeRepository;
import billing.sieunojt.domain.exchange.repository.exchangeOrder.ExchangeOrderRepository;
import billing.sieunojt.domain.exchange.service.ExchangeService;
import billing.sieunojt.domain.sale.repository.SaleRepository;
import billing.sieunojt.domain.sale.service.SaleService;
import billing.sieunojt.domain.spend.repository.SpendRepository;
import billing.sieunojt.domain.spend.service.SpendService;
import billing.sieunojt.domain.user.repository.UserRepository;
import billing.sieunojt.domain.user.service.UserService;
import billing.sieunojt.fake.repository.FakeExchangeOrderRepository;
import billing.sieunojt.fake.repository.FakeExchangeRepository;
import billing.sieunojt.fake.repository.FakeSaleRepository;
import billing.sieunojt.fake.repository.FakeSpendRepository;
import billing.sieunojt.fake.repository.FakeUserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class FakeConstructor {
    private final SaleRepository saleRepository = new FakeSaleRepository();
    private final UserRepository userRepository = new FakeUserRepository();
    private final ExchangeRepository exchangeRepository = new FakeExchangeRepository();
    private final ExchangeOrderRepository exchangeOrderRepository = new FakeExchangeOrderRepository();
    private final SpendRepository spendRepository = new FakeSpendRepository();


    private final UserService userService = new UserService(userRepository);
    private final ExchangeService exchangeService = new ExchangeService(exchangeOrderRepository, exchangeRepository, null, userService);
    private final SpendService spendService = new SpendService(spendRepository, exchangeService);
    private final SaleService saleService = new SaleService(saleRepository, exchangeService, userService, spendService);

//    public UserService getUserService(){
//        return this.userService;
//    }
//
//    public SaleService getSaleService(){
//        return this.saleService;
//    }
//    public ExchangeService getExchangeService(){
//        return this.exchangeService;
//    }
//
//    public SpendService getSpendService(){
//        return this.spendService;
//    }
//
//    public UserRepository getUserRepository(){
//        return this.userRepository;
//    }
}
