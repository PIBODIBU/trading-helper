package com.helper.trading.repository;

import com.helper.trading.model.Transaction;
import com.helper.trading.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository("TransactionRepository")
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    Transaction getFirstById(Long id);

    Set<Transaction> getAllByUser(User user);
}
