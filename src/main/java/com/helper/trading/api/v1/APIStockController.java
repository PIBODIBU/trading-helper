package com.helper.trading.api.v1;

import com.helper.trading.model.Stock;
import com.helper.trading.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stock")
public class APIStockController {
    private StockService stockService;

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Page<Stock> list(Pageable pageable) {
        return stockService.getPaged(pageable);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public Stock list(@RequestParam("stock_id") Long id) {
        return stockService.get(id);
    }
}
