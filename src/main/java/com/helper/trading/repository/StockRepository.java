package com.helper.trading.repository;

import com.helper.trading.model.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
    Set<Stock> findAll();
}
