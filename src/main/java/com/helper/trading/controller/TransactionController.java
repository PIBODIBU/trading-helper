package com.helper.trading.controller;

import com.google.gson.Gson;
import com.helper.trading.application.configuration.security.SecurityService;
import com.helper.trading.model.Transaction;
import com.helper.trading.service.TransactionService;
import com.helper.trading.util.StockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Set;

@Controller
@RequestMapping("/tx")
public class TransactionController {
    private Gson gson;
    private SecurityService securityService;

    @Autowired
    public void setGson(@Qualifier("Gson") Gson gson) {
        this.gson = gson;
    }

    @Autowired
    public void setSecurityService(@Qualifier("SecurityService") SecurityService securityService) {
        this.securityService = securityService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() throws IOException {
        ModelAndView modelAndView = new ModelAndView("index.jsp");

        modelAndView.addObject("user", gson.toJson(securityService.getUserFromContext()));

        return modelAndView;
    }
}
