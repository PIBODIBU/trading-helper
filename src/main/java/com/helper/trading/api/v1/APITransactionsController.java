package com.helper.trading.api.v1;

import com.google.gson.Gson;
import com.helper.trading.model.Transaction;
import com.helper.trading.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tx")
public class APITransactionsController {
    private TransactionService transactionService;
    private Gson gson;

    private static final Logger log = LoggerFactory.getLogger(APITickerController.class);

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Autowired
    public void setGson(@Qualifier("Gson") Gson gson) {
        this.gson = gson;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    Page<Transaction> list(Pageable pageable) {
        return transactionService.getMyByPage(pageable);
    }
}
