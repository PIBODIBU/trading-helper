package com.helper.trading.service;

import com.helper.trading.model.CurrencyPair;
import com.helper.trading.repository.CurrencyPairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CurrencyPairService {
    private CurrencyPairRepository repository;

    @Autowired
    public void setRepository(CurrencyPairRepository repository) {
        this.repository = repository;
    }

    public CurrencyPair get(Long id) {
        return repository.findOne(id);
    }

    public CurrencyPair get(String name){
        return repository.findFirstByName(name);
    }
}
