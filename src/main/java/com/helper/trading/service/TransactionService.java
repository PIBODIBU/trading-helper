package com.helper.trading.service;

import com.helper.trading.application.configuration.security.SecurityService;
import com.helper.trading.model.Stock;
import com.helper.trading.model.Transaction;
import com.helper.trading.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service("TransactionService")
@Transactional(value = "txManager", readOnly = true)
public class TransactionService {
    private TransactionRepository repository;
    private SecurityService securityService;
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public void setRepository(@Qualifier("TransactionRepository")
                                      TransactionRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setSecurityService(@Qualifier("SecurityService") SecurityService securityService) {
        this.securityService = securityService;
    }

    public Set<Transaction> getMy() {
        return repository.getAllByUser(securityService.getUserFromContext());
    }

    public Page<Transaction> getMyByPage(Pageable pageable) {
        return repository.findAllByUser(pageable, securityService.getUserFromContext());
    }

    public Set<Transaction> getMyByStock(Stock stock) {
        log.info("Stock id: " + String.valueOf(stock.getId()));
        log.info("User id: " + String.valueOf(securityService.getUserFromContext().getId()));

        return repository.findAllByUserAndStock(securityService.getUserFromContext(), stock);
    }

    public Transaction add(Transaction transaction) {
        return repository.save(transaction);
    }
}