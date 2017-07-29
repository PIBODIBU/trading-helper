package com.helper.trading.api.v1;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.service.trade.params.DefaultTradeHistoryParamCurrencyPair;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tx/history")
public class APITxHistoryController {
    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public List<UserTrade> get() throws IOException {
        ExchangeSpecification spec = new PoloniexExchange().getDefaultExchangeSpecification();
        spec.setUserName("romeo97934@gmail.com");
        spec.setApiKey("8RYK4NEK-5WM9MHSW-8XV4QFK2-4WKF16UZ");
        spec.setSecretKey("0c3c4fe2edf25dd21493d8b5cf8d149848e4e7b8b86c9956172a0f01f6f8288fa203dda1b01777592254474833e3e8bd378dd98221439c6aabac7c52ab99b7ab");

        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName());

        exchange.getTradeService();

        List<UserTrade> userTradeList = exchange.getTradeService()
                .getTradeHistory(new DefaultTradeHistoryParamCurrencyPair(new CurrencyPair("BTC/USD")))
                .getUserTrades();

        return userTradeList;
    }
}