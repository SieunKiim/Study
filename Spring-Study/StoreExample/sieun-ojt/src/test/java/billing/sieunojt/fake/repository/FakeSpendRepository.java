package billing.sieunojt.fake.repository;

import billing.sieunojt.domain.sale.model.entity.Sale;
import billing.sieunojt.domain.spend.model.dto.SpendDto.SpendExchangeDto;
import billing.sieunojt.domain.spend.model.entity.Spend;
import billing.sieunojt.domain.spend.repository.SpendRepository;
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

public class FakeSpendRepository implements SpendRepository {

    private Long autoIncrement = 0L;
    private final LinkedHashMap<Long, Spend> repository;

    public FakeSpendRepository(){
        this.repository = new LinkedHashMap<>();
    }

    @Override
    public List<SpendExchangeDto> findAllUnExpiredSpendExchangeInfo(long saleId) {
        return null;
    }

    @Override
    public List<Spend> findAll() {
        return null;
    }

    @Override
    public List<Spend> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Spend> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Spend> findAllById(Iterable<Long> longs) {
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
    public void delete(Spend entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Spend> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Spend> S save(S entity) {
        entity.autoIncreaseId(++autoIncrement);
        repository.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Spend> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Spend> findById(Long aLong) {
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
    public <S extends Spend> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Spend> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Spend> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Spend getOne(Long aLong) {
        return null;
    }

    @Override
    public Spend getById(Long aLong) {
        return null;
    }

    @Override
    public Spend getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Spend> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Spend> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Spend> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Spend> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Spend> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Spend> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Spend, R> R findBy(Example<S> example,
            Function<FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<Spend> findAllBySaleId(Long saleId) {
        List<Spend> output = new ArrayList<>();
        for (Entry<Long, Spend> entry : repository.entrySet()) {
            Spend spend = entry.getValue();
            if (spend.getSaleId().equals(saleId)) {
                output.add(spend);
            }
        }
        return output;
    }
}
