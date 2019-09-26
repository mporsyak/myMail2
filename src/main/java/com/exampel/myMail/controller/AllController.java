package com.exampel.myMail.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
//@ComponentScan("com.exampel.myMail.service")
public class AllController { //TODO: server-side rendering and client-side rendering
    @GetMapping("/greeting")
    public String greeting() {
        return "greeting";
    }

    @GetMapping(value="/login")
    public String login(){
        return "login";
    }

    @GetMapping(value="/register")
    public String register(){
        return "register";
    }

    @GetMapping(value = "/addMessege")//TODO: Stateless and statefull
    public String addMessege(Principal principal){
//        return new ModelAndView("/addMessege", "authUser", principal.getName());
        return "addMessege";
    }

    @GetMapping(path = "/addUser")
    public String addUser() {
        return "addUser";
    }

    @GetMapping(path = "/allMessege")
    public String allMessege() {
        return "allMessege";
    }

    @GetMapping (path = "/all")
    public String all() {
        return "all";
    }
}