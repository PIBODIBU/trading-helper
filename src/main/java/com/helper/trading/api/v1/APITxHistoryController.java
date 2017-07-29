package com.helper.trading.api.v1;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tx/history")
public class APITxHistoryController {
    public List<UserTrade> get() throws IOException {
        ExchangeSpecification spec = new BitstampExchange().getDefaultExchangeSpecification();
        spec.setUserName("");
        spec.setApiKey("");
        spec.setSecretKey("");
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);
        exchange.getTradeService();

        List<UserTrade> userTradeList = exchange.getTradeService()
                .getTradeHistory(new DefaultTradeHistoryParamCurrencyPair(new CurrencyPair("BTC/USD")))
                .getUserTrades();

        return userTradeList;
    }
}