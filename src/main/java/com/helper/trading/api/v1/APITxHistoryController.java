package com.helper.trading.api.v1;

import com.helper.trading.application.configuration.security.SecurityService;
import com.helper.trading.model.CurrencyPair;
import com.helper.trading.model.Stock;
import com.helper.trading.model.Transaction;
import com.helper.trading.model.TxType;
import com.helper.trading.service.TransactionService;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.Order;
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
import java.util.Date;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/tx/history")
public class APITxHistoryController {
    private static final Logger log = LoggerFactory.getLogger(APITxHistoryController.class);

    private TransactionService transactionService;
    private SecurityService securityService;

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

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
        params.setStartTime(new Date(1498780800000L));
        params.setEndTime(new Date());

        List<UserTrade> list = exchange
                .getTradeService()
                .getTradeHistory(exchange.getTradeService().createTradeHistoryParams())
                .getUserTrades();

        log.info("Trades of:");
        log.info("Username: ", spec.toString());
        log.info("Size: ", list.size());

        for (UserTrade trade : list) {
            log.info(trade.toString());
        }

        return list;
    }

    @RequestMapping(value = "/sync", method = RequestMethod.GET)
    public void sync() throws IOException {
        ExchangeSpecification spec = new PoloniexExchange().getDefaultExchangeSpecification();
        spec.setUserName("romeo97934@gmail.com");
        spec.setApiKey("Z131FF6M-Z97OAOSN-S2CGX4QV-23LA20ER");
        spec.setSecretKey("cf11acd600871a8144e6fb3d356b38015d5cc2c24a84a1d54dafca59597703947f2aea19679d5ed76a9b22129d08b6d3adf36fb787d7c43df82a3d0da4b888f3");

        Exchange exchange = ExchangeFactory.INSTANCE.createExchange(spec);
        PoloniexTradeService.PoloniexTradeHistoryParams params =
                ((PoloniexTradeService.PoloniexTradeHistoryParams) exchange.getTradeService().createTradeHistoryParams());
        List<UserTrade> userTrades = exchange
                .getTradeService()
                .getTradeHistory(exchange.getTradeService().createTradeHistoryParams())
                .getUserTrades();

        Stock poloStock = new Stock();
        poloStock.setId(3L);
        Set<Transaction> poloTxs = transactionService.getMyByStock(poloStock);

        for (UserTrade trade : userTrades) {
            for (Transaction tx : poloTxs) {
                if (tx.getTxId() != null)
                    if (!tx.getTxId().equals(Long.valueOf(trade.getId()))) {
                        Transaction newTx = new Transaction();
                        CurrencyPair currencyPair = new CurrencyPair();
                        TxType txType = new TxType();

                        if (trade.getType().equals(Order.OrderType.ASK)) {
                            txType.setId(3L);
                        } else {
                            txType.setId(4L);
                        }

                        currencyPair.setId(1L);

                        newTx.setTxId(Long.valueOf(trade.getId()));
                        newTx.setUser(securityService.getUserFromContext());
                        newTx.setStock(poloStock);
                        newTx.setCurrencyPair(currencyPair);
                        newTx.setTxType(txType);
                        newTx.setTradePrice(trade.getPrice().doubleValue());
                        newTx.setQuantity(trade.getTradableAmount().doubleValue());
                        newTx.setTotal(newTx.getQuantity() * newTx.getQuantity());
                        newTx.setTradeDate(trade.getTimestamp());

                        transactionService.add(newTx);
                    }
            }
        }
    }
}