package com.helper.trading.repository;

import com.helper.trading.model.Transaction;
import com.helper.trading.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository("TransactionRepository")
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    Transaction getFirstById(Long id);

    Set<Transaction> getAllByUser(User user);

    Page<Transaction> findAllByUser(Pageable pageable, User user);
}
