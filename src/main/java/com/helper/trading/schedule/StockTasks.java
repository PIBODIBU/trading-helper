package com.helper.trading.schedule;

import com.helper.trading.model.Stock;
import com.helper.trading.service.CurrencyPairService;
import com.helper.trading.service.StockService;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class StockTasks {
    private StockService stockService;
    private CurrencyPairService currencyPairService;

    private static final Logger log = LoggerFactory.getLogger(StockTasks.class);

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @Autowired
    public void setCurrencyPairService(CurrencyPairService currencyPairService) {
        this.currencyPairService = currencyPairService;
    }

    @Scheduled(fixedRate = 300000) // 5 min
    public void checkStocksPairs() {
        Set<Stock> stocks = stockService.getAll();
        Set<com.helper.trading.model.CurrencyPair> stockPairs;
        Exchange exchange;
        boolean alreadyExists, hasChanged;

        for (Stock stock : stocks) {
            exchange = null;

            try {
                exchange = ExchangeFactory.INSTANCE.createExchange(stock.getNameJava());
            } catch (Exception ignored) {
            }

            if (exchange == null)
                continue;

            stockPairs = stock.getCurrencyPairs();
            hasChanged = false;

            if (stockPairs == null)
                stockPairs = new HashSet<>();

            log.info("Stock: " + stock.getName());

            for (CurrencyPair exchangePair : exchange.getExchangeSymbols()) {
                alreadyExists = false;

                log.info("Pair: " + exchangePair.toString());

                for (com.helper.trading.model.CurrencyPair dbPair : stockPairs) {
                    if (dbPair != null && exchangePair != null)
                        if (dbPair.getName().equals(exchangePair.toString()))
                            alreadyExists = true;
                }

                // Pair doesn't exist in stock's pairs. Need to get it from db and add to stock's pairs set
                if (!alreadyExists) {
                    // Get pair from db by name
                    com.helper.trading.model.CurrencyPair c = currencyPairService.get(exchangePair.toString());

                    // Check if db contains such currency pair
                    if (c != null) {
                        log.info("Adding with id: " + String.valueOf(c.getId()));

                        stockPairs.add(c);
                        hasChanged = true;
                    }
                }
            }

            // Stock model has changed. Need to refresh it
            if (hasChanged) {
                stock.setCurrencyPairs(stockPairs);
                Long id = stockService.update(stock);

                log.info("Updated stock: " + String.valueOf(id));
            }
        }
    }
}