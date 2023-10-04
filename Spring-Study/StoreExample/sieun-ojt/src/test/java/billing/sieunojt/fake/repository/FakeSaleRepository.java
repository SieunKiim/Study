package billing.sieunojt.fake.repository;

import billing.sieunojt.domain.sale.model.dto.SaleDto.BuyHistoryResponse;
import billing.sieunojt.domain.sale.model.dto.SaleDto.BuyItemInfo;
import billing.sieunojt.domain.sale.model.dto.SaleDto.RefundHistoryResponse;
import billing.sieunojt.domain.sale.model.dto.SaleDto.SaleItemInfo;
import billing.sieunojt.domain.sale.model.entity.Sale;
import billing.sieunojt.domain.sale.repository.SaleRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

public class FakeSaleRepository implements SaleRepository {

    private Long autoIncrement = 0L;

    private final HashMap<Long, Sale> repository;

    public FakeSaleRepository(){
        this.repository = new HashMap<>();
    }


    @Override
    public Page<BuyHistoryResponse> getBuyHistory(long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<RefundHistoryResponse> getRefundHistory(long userId, Pageable pageable) {
        return null;
    }

    @Override
    public BuyItemInfo getSaleRequires(long userId, long itemId) {
        return new BuyItemInfo(1L, 1L, 5000, 100, "일번아이템");
    }

    @Override
    public SaleItemInfo getSaleItemInfo(Long saleId) {
        return null;
    }

    @Override
    public Boolean existsByTargetSaleId(long saleId) {
        return null;
    }

    @Override
    public List<Sale> findAll() {
        return null;
    }

    @Override
    public List<Sale> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Sale> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Sale> findAllById(Iterable<Long> longs) {
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
    public void delete(Sale entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Sale> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Sale> S save(S entity) {
        entity.autoIncreaseId(++autoIncrement);
        repository.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public <S extends Sale> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Sale> findById(Long aLong) {
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
    public <S extends Sale> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Sale> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Sale> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Sale getOne(Long aLong) {
        return null;
    }

    @Override
    public Sale getById(Long aLong) {
        return null;
    }

    @Override
    public Sale getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Sale> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Sale> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Sale> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Sale> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Sale> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Sale> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Sale, R> R findBy(Example<S> example,
            Function<FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
