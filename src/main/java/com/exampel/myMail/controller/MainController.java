package com.exampel.myMail.controller;

import com.exampel.myMail.model.User;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
//@ComponentScan("com.exampel.myMail.service")
public class MainController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/addUser")
    public String addNewUser() {
        return "addUser";
    }

    @PostMapping(path = "/addUser")
    public ModelAndView addUser(@RequestParam String login, @RequestParam String password) {
        if(userService.findByLogin(login)!=null){
            return new ModelAndView("greeting", "loginExist", "Логин занят");
        }
//        List<User> list = userService.getAllUser();
//        for (int i = 0; i < list.size(); i++) {
//            if(list.get(i).getLogin().equals(login)){
//                return new ModelAndView("greeting", "loginExist", "Логин занят");
//            }
//        }
        User n = new User();
        n.setLogin(login);
        n.setPassword(password);
        n.setEmail(login+"@pm.ru");

        userService.addUser(n);
        return new ModelAndView("greeting", "userList", userService.getAllUser());
    }

    @GetMapping (path = "/all")
    public ModelAndView getAllUsers(Principal principal) {
        ModelAndView model = new ModelAndView("/all");
        model.addObject("userList", userService.getAllUser());
        model.addObject("loggedUser", principal.getName());

        return model;
//        return new ModelAndView("all", "userList", userService.getAllUser());
    }
}
