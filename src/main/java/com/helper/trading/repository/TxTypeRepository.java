package com.helper.trading.repository;

import com.helper.trading.model.TxType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TxTypeRepository extends PagingAndSortingRepository<TxType, Long> {
    TxType findFirstByName(String name);
}
