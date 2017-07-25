package com.helper.trading.repository;

import com.helper.trading.model.CurrencyPair;
import com.helper.trading.model.CurrencyRate;
import com.helper.trading.model.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CurrencyRateRepository extends CrudRepository<CurrencyRate, Long> {
    Set<CurrencyRate> findAll();

    CurrencyRate getFirstByStockAndCurrencyPair(Stock stock, CurrencyPair currencyPair);

    Set<CurrencyRate> getAllByCurrencyPair(CurrencyPair currencyPair);

    Set<CurrencyRate> getAllByStock(Stock stock);
}
