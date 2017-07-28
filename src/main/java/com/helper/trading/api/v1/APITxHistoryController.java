package com.helper.trading.api.v1;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitstamp.BitstampExchange;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tx/history")
public class APITxHistoryController {
    public String get() {
        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitstampExchange.class.getName());
        exchange.getTradeService();

        return "";
    }
}
