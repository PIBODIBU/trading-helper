package com.helper.trading.schedule;

import com.helper.trading.model.CurrencyRate;
import com.helper.trading.model.Stock;
import com.helper.trading.service.CurrencyRateService;
import com.helper.trading.service.StockService;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Component
public class CurrencyRateTasks {
    private CurrencyRateService rateService;
    private StockService stockService;

    private static final Logger log = LoggerFactory.getLogger(CurrencyRateTasks.class);

    @Autowired
    public void setRateService(CurrencyRateService rateService) {
        this.rateService = rateService;
    }

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    //@Scheduled(fixedRate = 60000) // 1 minute
    public void checkCurrencyRateDbRecords() {
        Set<CurrencyRate> currencyRates = rateService.getAll();
        Set<Stock> stocks = stockService.getAll();
        boolean alreadyExists;

        for (Stock stock : stocks) {
            for (com.helper.trading.model.CurrencyPair pair : stock.getCurrencyPairs()) {
                alreadyExists = false;

                for (CurrencyRate rate : currencyRates) {
                    if (rate.getCurrencyPair().equals(pair) && rate.getStock().equals(stock))
                        alreadyExists = true;
                }

                if (!alreadyExists) {
                    CurrencyRate newItem = new CurrencyRate();
                    newItem.setRate(0D);
                    newItem.setDateUpdated(new Date());
                    newItem.setStock(stock);
                    newItem.setCurrencyPair(pair);

                    rateService.add(newItem);
                }
            }
        }
    }

    //@Scheduled(fixedRate = 30000) // 30 sec
    public void updateCurrencyRateRecords() {
        Set<CurrencyRate> rates = rateService.getAll();
        HashMap<String, Exchange> exchanges = new ManagedMap<>();

        for (CurrencyRate rate : rates) {
            String dbStockName = rate.getStock().getName();
            String dbStockJavaName = rate.getStock().getNameJava();

            // Check if Exchange already created
            if (exchanges.get(dbStockJavaName) == null) {
                // Exchange needed to be initialized
                try {
                    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(dbStockJavaName);
                    exchanges.put(dbStockJavaName, exchange);
                } catch (Exception ex) {
                    log.error("Error occurred during exchange initialization: " + dbStockName);
                }
            }

            // Check if exchange was created
            if (exchanges.get(dbStockJavaName) == null)
                continue;

            // Get market service
            MarketDataService marketDataService = exchanges.get(dbStockJavaName).getMarketDataService();

            // Get rate for current item
            try {
                Ticker ticker = marketDataService.getTicker(new CurrencyPair(rate.getCurrencyPair().getName()));

                // Update item's rate with ticker 'last' price
                rate.setRate(ticker.getLast().doubleValue());
                rate.setDateUpdated(new Date());
            } catch (Exception e) {
                log.error("Error occurred during rate updating. Details: " +
                        "\n Stock: " + dbStockName +
                        "\nPair: " + rate.getCurrencyPair().getName() +
                        "\n" + e.getMessage());
            }
        }

        // Update db records
        rateService.performComplexUpdate(rates);
    }
}