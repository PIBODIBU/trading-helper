package com.helper.trading.api.v1;

import com.helper.trading.model.Stock;
import com.helper.trading.model.Transaction;
import com.helper.trading.service.StockService;
import com.helper.trading.service.TransactionService;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.trade.UserTrade;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.poloniex.service.PoloniexTradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/tx/history")
public class APITxHistoryController {
    private static final Logger log = LoggerFactory.getLogger(APITxHistoryController.class);

    private TransactionService transactionService;
    private StockService stockService;

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public List<UserTrade> get() throws IOException {
        ExchangeSpecification spec = new PoloniexExchange().getDefaultExchangeSpecification();
        Exchange exchange;
        PoloniexTradeService.PoloniexTradeHistoryParams params;

        spec.setUserName("romeo97934@gmail.com");
        spec.setApiKey("Z131FF6M-Z97OAOSN-S2CGX4QV-23LA20ER");
        spec.setSecretKey("cf11acd600871a8144e6fb3d356b38015d5cc2c24a84a1d54dafca59597703947f2aea19679d5ed76a9b22129d08b6d3adf36fb787d7c43df82a3d0da4b888f3");

        exchange = ExchangeFactory.INSTANCE.createExchange(spec);
        params =
                ((PoloniexTradeService.PoloniexTradeHistoryParams) exchange.getTradeService().createTradeHistoryParams());

        params.setStartTime(new Date(1L));
        params.setEndTime(new Date());

        List<UserTrade> list = exchange
                .getTradeService()
                .getTradeHistory(params)
                .getUserTrades();

        log.info("Size: ", list.size());

        for (UserTrade trade : list) {
            log.info(trade.toString());
        }

        return list;
    }

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    public void sync() throws IOException {
        Set<Transaction> newTxs = new HashSet<>();
        ExchangeSpecification spec = new PoloniexExchange().getDefaultExchangeSpecification();
        Exchange exchange;
        PoloniexTradeService.PoloniexTradeHistoryParams params;

        spec.setUserName("romeo97934@gmail.com");
        spec.setApiKey("Z131FF6M-Z97OAOSN-S2CGX4QV-23LA20ER");
        spec.setSecretKey("cf11acd600871a8144e6fb3d356b38015d5cc2c24a84a1d54dafca59597703947f2aea19679d5ed76a9b22129d08b6d3adf36fb787d7c43df82a3d0da4b888f3");

        exchange = ExchangeFactory.INSTANCE.createExchange(spec);
        params = ((PoloniexTradeService.PoloniexTradeHistoryParams) exchange.getTradeService().createTradeHistoryParams());

        params.setStartTime(new Date(1L));
        params.setEndTime(new Date());

        List<UserTrade> userTrades = exchange
                .getTradeService()
                .getTradeHistory(params)
                .getUserTrades();

        Stock poloStock = stockService.get(3L);

        Set<Transaction> poloTxs = transactionService.getMyByStock(poloStock);

        if (poloTxs == null || poloTxs.size() == 0) {
            for (UserTrade trade : userTrades)
                newTxs.add(transactionService.fromUserTrade(trade));

            transactionService.add(newTxs);
            return;
        }

        for (UserTrade trade : userTrades) {
            for (Transaction tx : poloTxs) {
                log.info("Poloniex tx id: " + String.valueOf(tx.getTxId()));
                log.info("User trade tx id: " + String.valueOf(Long.valueOf(trade.getId())));
                log.info("Equals 1? " + (tx.getTxId() != Long.valueOf(trade.getId())));
                log.info("Equals 2? " + (tx.getTxId().equals(Long.valueOf(trade.getId()))));
                log.info("Equals 3? " + (Objects.equals(tx.getTxId(), Long.valueOf(trade.getId()))));

                if (tx.getTxId() != null && tx.getTxId() != Long.valueOf(trade.getId()))
                    newTxs.add(transactionService.fromUserTrade(trade));
            }

            transactionService.add(newTxs);
        }
    }
}