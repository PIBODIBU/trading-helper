package com.helper.trading.util;

import org.knowm.xchange.btce.v3.BTCEExchange;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class StockUtil {
    private HashMap<String, String> stocks;

    public StockUtil() {
        stocks = new HashMap<>();

        stocks.put("BTC-E", BTCEExchange.class.getName());
    }

    public HashMap<String, String> getStocks() {
        return stocks;
    }
}
