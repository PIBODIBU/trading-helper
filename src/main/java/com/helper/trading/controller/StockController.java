package com.helper.trading.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.helper.trading.application.configuration.security.SecurityService;
import com.helper.trading.service.UserService;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btce.v3.BTCEExchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.utils.CertHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/stock")
public class StockController {
    private SecurityService securityService;
    private UserService userService;
    private Gson gson;

    @Autowired
    public void setSecurityService(@Qualifier("SecurityService")
                                           SecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired
    public void setUserService(@Qualifier("UserService") UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setGson(@Qualifier("Gson") Gson gson) {
        this.gson = gson;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test() {
        ModelAndView modelAndView = new ModelAndView("stock.jsp");

        //modelAndView.addObject("user", gson.toJson(securityService.getUserFromContext()));

        ExchangeSpecification spec = new ExchangeSpecification(PoloniexExchange.class);
        spec.setApiKey("8RYK4NEK-5WM9MHSW-8XV4QFK2-4WKF16UZ");
        spec.setSecretKey("0c3c4fe2edf25dd21493d8b5cf8d149848e4e7b8b86c9956172a0f01f6f8288fa203dda1b01777592254474833e3e8bd378dd98221439c6aabac7c52ab99b7ab");

        Exchange btce = ExchangeFactory.INSTANCE.createExchange(BTCEExchange.class.getName());

        MarketDataService marketDataService = btce.getMarketDataService();
        Ticker ticker = null;

        try {
            ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ticker != null) {
            modelAndView.addObject("ticker", gson.toJson(ticker));
            modelAndView.addObject("ticker_gen", new GsonBuilder().create().toJson(ticker));
        }

        return modelAndView;
    }
}
