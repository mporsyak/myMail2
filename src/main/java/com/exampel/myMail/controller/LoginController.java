package com.exampel.myMail.controller;

import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {
    @Autowired
    UserService userService;
    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String start(Model model){

        return "login";
    }
}
