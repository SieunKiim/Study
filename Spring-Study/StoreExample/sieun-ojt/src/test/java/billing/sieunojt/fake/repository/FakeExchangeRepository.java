package billing.sieunojt.fake.repository;

import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.BuyDiamondHistoryResponse;
import billing.sieunojt.domain.exchange.model.dto.ExchangeDto.RefundDiamondHistoryResponse;
import billing.sieunojt.domain.exchange.model.entity.Exchange;
import billing.sieunojt.domain.exchange.repository.exchange.ExchangeRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

public class FakeExchangeRepository implements ExchangeRepository {

    private Long autoIncrement = 0L;
    private final LinkedHashMap<Long, Exchange> repository;

    public FakeExchangeRepository(){
        this.repository = new LinkedHashMap<>();
    }

    @Override
    public List<BuyDiamondHistoryResponse> findAllBuyExchanges(long userId) {
        return null;
    }

    @Override
    public List<RefundDiamondHistoryResponse> findAllRefundExchanges(long userId) {
        return null;
    }

    @Override
    public List<Exchange> findAvailableExchange(long userId) {
        List<Exchange> output = new ArrayList<>();
        for (Entry<Long, Exchange> entry : repository.entrySet()) {
            Exchange exchange = entry.getValue();
            if (exchange.getUserId().equals(userId) && exchange.getLeftDiamond() > 0) {
                output.add(exchange);
            }
        }
        return output;
    }

    @Override
    public List<Exchange> findAllRefundableInId(List<Long> ids) {
        return null;
    }

    @Override
    public Optional<Exchange> findByPartnerOrderId(String id) {
        return Optional.empty();
    }

    @Override
    public Boolean existsByPartnerOrderIdAndIsRefund(String id, Boolean isRefund) {
        return null;
    }

    @Override
    public List<Exchange> findAllByExpiredAtBeforeAndLeftDiamondNot(LocalDateTime date, int lef) {
        return null;
    }

    @Override
    public List<Exchange> findAllByIdIn(List<Long> ids) {
        List<Exchange> output = new ArrayList<>();
        for (Entry<Long, Exchange> entry : repository.entrySet()) {
            Exchange exchange = entry.getValue();
            if (ids.contains(exchange.getId())) {
                output.add(exchange);
            }
        }
        return output;
    }

    @Override
    public List<Exchange> findAll() {
        List<Exchange> output = new ArrayList<>();
        for (Entry<Long, Exchange> entry : repository.entrySet()) {
            output.add(entry.getValue());
        }
        return output;
    }

    @Override
    public List<Exchange> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Exchange> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Exchange> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Exchange entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Exchange> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Exchange> S save(S entity) {
        entity.autoIncreaseId(++autoIncrement);
        repository.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Exchange> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Exchange> findById(Long aLong) {
        return Optional.ofNullable(repository.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Exchange> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Exchange> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Exchange> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Exchange getOne(Long aLong) {
        return null;
    }

    @Override
    public Exchange getById(Long aLong) {
        return null;
    }

    @Override
    public Exchange getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Exchange> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Exchange> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Exchange> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Exchange> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Exchange> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Exchange> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Exchange, R> R findBy(Example<S> example,
            Function<FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
