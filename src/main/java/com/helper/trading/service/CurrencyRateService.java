package com.helper.trading.service;

import com.helper.trading.model.CurrencyPair;
import com.helper.trading.model.CurrencyRate;
import com.helper.trading.model.Stock;
import com.helper.trading.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional(value = "txManager")
public class CurrencyRateService {
    private CurrencyRateRepository repository;

    @Autowired
    public void setRepository(CurrencyRateRepository repository) {
        this.repository = repository;
    }

    public CurrencyRate get(Stock stock, CurrencyPair pair) {
        return repository.getFirstByStockAndCurrencyPair(stock, pair);
    }

    public Set<CurrencyRate> getAll() {
        return repository.findAll();
    }

    public void update(CurrencyRate rate) {
        repository.save(rate);
    }

    public void add(CurrencyRate rate) {
        repository.save(rate);
    }

    public void performComplexUpdate(Set<CurrencyRate> rates) {
        repository.save(rates);
    }

    public Page<CurrencyRate> paged(Pageable pageable){
        return repository.findAll(pageable);
    }
}
