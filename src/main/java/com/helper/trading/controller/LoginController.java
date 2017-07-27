package com.helper.trading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView simplePage() {
        ModelAndView modelAndView = new ModelAndView("login.jsp");

        return modelAndView;
    }
}