package billing.sieunojt.fake.service;

import billing.sieunojt.domain.exchange.service.ExchangeService;
import billing.sieunojt.domain.sale.repository.SaleRepository;
import billing.sieunojt.domain.sale.service.SaleService;
import billing.sieunojt.domain.spend.service.SpendService;
import billing.sieunojt.domain.user.service.UserService;

public class FakeSaleService extends SaleService {


    public FakeSaleService(SaleRepository saleRepository,
            ExchangeService exchangeService,
            UserService userService,
            SpendService spendService) {
        super(saleRepository, exchangeService, userService, spendService);
    }


}
