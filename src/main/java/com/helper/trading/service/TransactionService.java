package com.helper.trading.service;

import com.helper.trading.application.configuration.security.SecurityService;
import com.helper.trading.model.Transaction;
import com.helper.trading.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service("TransactionService")
@Transactional(value = "txManager", readOnly = true)
public class TransactionService {
    private TransactionRepository transactionRepository;

    private SecurityService securityService;

    @Autowired
    public void setTransactionRepository(@Qualifier("TransactionRepository")
                                                     TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Autowired
    public void setSecurityService(@Qualifier("SecurityService") SecurityService securityService) {
        this.securityService = securityService;
    }

    public Set<Transaction> getMyTransactions(){
        return transactionRepository.getAllByUser(securityService.getUserFromContext());
    }
}
