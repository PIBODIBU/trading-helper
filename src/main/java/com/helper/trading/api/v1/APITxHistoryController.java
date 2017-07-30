package com.helper.trading.api.v1;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.poloniex.service.PoloniexTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tx/history")
public class APITxHistoryController {
    private static final Logger log = LoggerFactory.getLogger(APITxHistoryController.class);

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public List<UserTrade> get() throws IOException {
        ExchangeSpecification spec = new PoloniexExchange().getDefaultExchangeSpecification();
        spec.setUserName("romeo97934@gmail.com");
        spec.setApiKey("Z131FF6M-Z97OAOSN-S2CGX4QV-23LA20ER");
        spec.setSecretKey("cf11acd600871a8144e6fb3d356b38015d5cc2c24a84a1d54dafca59597703947f2aea19679d5ed76a9b22129d08b6d3adf36fb787d7c43df82a3d0da4b888f3");

        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);

        PoloniexTradeService.PoloniexTradeHistoryParams params =
                ((PoloniexTradeService.PoloniexTradeHistoryParams) exchange.getTradeService().createTradeHistoryParams());
//        params.setCurrencyPair(new CurrencyPair("STEEM/BTC"));

        List<UserTrade> list = exchange.getTradeService()
                .getTradeHistory(params)
                .getUserTrades();

        log.info("Trades of:");
        log.info("Username: ", spec.toString());

        for (UserTrade trade : list) {
            log.info(trade.toString());
        }

        return list;
    }
}