package com.helper.trading.repository;

import com.helper.trading.model.CurrencyPair;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyPairRepository extends CrudRepository<CurrencyPair, Long> {
}
