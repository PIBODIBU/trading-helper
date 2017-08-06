package com.helper.trading.api.v1;

import com.helper.trading.model.CurrencyRate;
import com.helper.trading.service.CurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rate")
public class APICurrencyRateController {
    private CurrencyRateService currencyRateService;

    @Autowired
    public void setCurrencyRateService(CurrencyRateService currencyRateService) {
        this.currencyRateService = currencyRateService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page<CurrencyRate> list(Pageable pageable) {
        return currencyRateService.paged(pageable);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, params = {"stock_id"})
    public Page<CurrencyRate> listByStock(@RequestParam("stock_id") Long stockId, Pageable pageable) {
        return currencyRateService.pagedByStock(pageable, stockId);
    }
}
