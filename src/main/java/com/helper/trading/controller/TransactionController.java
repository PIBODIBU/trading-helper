package com.helper.trading.controller;

import com.google.gson.Gson;
import com.helper.trading.application.configuration.security.SecurityService;
import com.helper.trading.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tx")
public class TransactionController {
    private Gson gson;
    private SecurityService securityService;
    private TransactionService transactionService;

    @Autowired
    public void setGson(@Qualifier("Gson") Gson gson) {
        this.gson = gson;
    }

    @Autowired
    public void setSecurityService(@Qualifier("SecurityService") SecurityService securityService) {
        this.securityService = securityService;
    }

    @Autowired
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("stock.jsp");

        modelAndView.addObject("user", gson.toJson(securityService.getUserFromContext()));
        modelAndView.addObject("transactions", gson.toJson(transactionService.getMyTransactions()));

        return modelAndView;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView add() {
        ModelAndView modelAndView = new ModelAndView("stock.jsp");


        return modelAndView;
    }
}
