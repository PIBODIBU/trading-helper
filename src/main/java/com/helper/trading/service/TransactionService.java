package com.helper.trading.service;

import com.helper.trading.application.configuration.security.SecurityService;
import com.helper.trading.model.CurrencyPair;
import com.helper.trading.model.Stock;
import com.helper.trading.model.Transaction;
import com.helper.trading.model.TxType;
import com.helper.trading.repository.TransactionRepository;
import org.knowm.xchange.dto.trade.UserTrade;
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
@Transactional(value = "txManager")
public class TransactionService {
    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);
    private TransactionRepository repository;
    private SecurityService securityService;
    private CurrencyPairService currencyPairService;
    private TxTypeService txTypeService;
    private StockService stockService;

    public Set<Transaction> getMy() {
        return repository.getAllByUser(securityService.getUserFromContext());
    }

    public Page<Transaction> getMyByPage(Pageable pageable) {
        return repository.findByUser(pageable, securityService.getUserFromContext());
    }

    public Set<Transaction> getMyByStock(Stock stock) {
        return repository.findByStock(stock);
    }

    public Transaction add(Transaction transaction) {
        return repository.save(transaction);
    }

    public void add(Set<Transaction> transactions) {
        repository.save(transactions);
    }

    public Transaction fromUserTrade(UserTrade userTrade) {
        Transaction newTx = new Transaction();
        Stock poloStock;
        CurrencyPair currencyPair;
        TxType txType;

        currencyPair = currencyPairService.get(userTrade.getCurrencyPair().toString());
        txType = txTypeService.fromUserTrade(userTrade);
        poloStock = stockService.get(2L);

        if (currencyPair == null)
            currencyPair = currencyPairService.get(1L);

        newTx.setTxId(Long.valueOf(userTrade.getId()));
        newTx.setUser(securityService.getUserFromContext());
        newTx.setStock(poloStock);
        newTx.setCurrencyPair(currencyPair);
        newTx.setTxType(txType);
        newTx.setTradePrice(userTrade.getPrice().doubleValue());
        newTx.setQuantity(userTrade.getTradableAmount().doubleValue());
        newTx.setTotal(newTx.getTradePrice() * newTx.getQuantity());
        newTx.setTradeDate(userTrade.getTimestamp());

        return newTx;
    }

    @Autowired
    public void setRepository(@Qualifier("TransactionRepository")
                                      TransactionRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setSecurityService(@Qualifier("SecurityService") SecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired
    public void setCurrencyPairService(CurrencyPairService currencyPairService) {
        this.currencyPairService = currencyPairService;
    }

    @Autowired
    public void setTxTypeService(TxTypeService txTypeService) {
        this.txTypeService = txTypeService;
    }

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }
}