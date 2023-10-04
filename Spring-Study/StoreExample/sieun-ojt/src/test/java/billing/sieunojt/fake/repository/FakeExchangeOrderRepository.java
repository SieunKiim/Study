package billing.sieunojt.fake.repository;

import billing.sieunojt.domain.exchange.model.entity.ExchangeOrder;
import billing.sieunojt.domain.exchange.repository.exchangeOrder.ExchangeOrderRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

public class FakeExchangeOrderRepository implements ExchangeOrderRepository {

    private Long autoIncrementId = 0L;
    private final HashMap<String, ExchangeOrder> repository;

    public FakeExchangeOrderRepository(){
        this.repository = new HashMap<>();
    }

    @Override
    public Optional<ExchangeOrder> findByPartnerOrderId(String orderId) {
        return Optional.empty();
    }

    @Override
    public List<ExchangeOrder> findAll() {
        return null;
    }

    @Override
    public List<ExchangeOrder> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<ExchangeOrder> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<ExchangeOrder> findAllById(Iterable<Long> longs) {
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
    public void delete(ExchangeOrder entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends ExchangeOrder> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends ExchangeOrder> S save(S entity) {
        repository.put(entity.getPartnerOrderId(), entity);
        return entity;
    }

    @Override
    public <S extends ExchangeOrder> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<ExchangeOrder> findById(Long aLong) {
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
    public <S extends ExchangeOrder> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends ExchangeOrder> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<ExchangeOrder> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public ExchangeOrder getOne(Long aLong) {
        return null;
    }

    @Override
    public ExchangeOrder getById(Long aLong) {
        return null;
    }

    @Override
    public ExchangeOrder getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends ExchangeOrder> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends ExchangeOrder> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends ExchangeOrder> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends ExchangeOrder> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends ExchangeOrder> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends ExchangeOrder> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends ExchangeOrder, R> R findBy(Example<S> example,
            Function<FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
