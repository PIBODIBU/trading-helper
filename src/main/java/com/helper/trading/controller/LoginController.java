package com.helper.trading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app")
public class LoginController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String simplePage(ModelMap modelMap) {
        return "login";
    }
}