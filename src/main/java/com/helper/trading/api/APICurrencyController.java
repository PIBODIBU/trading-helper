package com.helper.trading.api;

import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.btce.v3.BTCEExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class APICurrencyController {
    @RequestMapping(value = "/ticker", method = RequestMethod.GET)
    public Ticker getTicker(@RequestParam(value = "currency_pair", defaultValue = "BTC/USD") String currencyPair) throws IOException {
        return ExchangeFactory.INSTANCE.createExchange(BTCEExchange.class.getName()).getMarketDataService().getTicker(new CurrencyPair(currencyPair));
    }
}
