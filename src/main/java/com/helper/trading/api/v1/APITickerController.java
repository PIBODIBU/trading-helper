package com.helper.trading.api.v1;

import com.google.gson.Gson;
import com.helper.trading.model.CurrencyPair;
import com.helper.trading.model.CurrencyRate;
import com.helper.trading.model.Stock;
import com.helper.trading.service.CurrencyPairService;
import com.helper.trading.service.CurrencyRateService;
import com.helper.trading.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/currency")
public class APITickerController {
    private StockService stockService;
    private CurrencyPairService pairService;
    private CurrencyRateService rateService;
    private Gson gson;

    private static final Logger log = LoggerFactory.getLogger(APITickerController.class);

    @Autowired
    public void setPairService(CurrencyPairService pairService) {
        this.pairService = pairService;
    }

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @Autowired
    public void setRateService(CurrencyRateService rateService) {
        this.rateService = rateService;
    }

    @Autowired
    public void setGson(@Qualifier("Gson") Gson gson) {
        this.gson = gson;
    }

    @RequestMapping(value = "/ticker", method = RequestMethod.GET)
    public String getTicker(@RequestParam(value = "currency_pair_id", defaultValue = "1") Long pairId,
                            @RequestParam(value = "stock_id", defaultValue = "1") Long stockId) throws IOException {
        // TODO validate parameters

        Stock stock = stockService.get(stockId);
        CurrencyPair currencyPair = pairService.get(pairId);

        CurrencyRate currencyRate = rateService.get(stock, currencyPair);

        if (currencyRate != null)
            log.info("Rate: " + currencyRate.getRate());
        else
            log.info("Rate object is null");

        return gson.toJson(currencyRate);
    }
}