package com.helper.trading.controller;

import com.helper.trading.application.configuration.security.SecurityService;
import com.helper.trading.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/me")
public class MyPageController {
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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView myPage() {
        ModelAndView modelAndView = new ModelAndView("my_page.jsp");

        modelAndView.addObject("user", gson.toJson(securityService.getUserFromContext()));

        return modelAndView;
    }
}
