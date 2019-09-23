package com.exampel.myMail.controller;

//import com.exampel.myMail.model.NewUser;
import com.exampel.myMail.model.User;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/addUser")
    public ResponseEntity<?> addNewUser(@RequestBody User addNewUser){
        String newLogin = addNewUser.getLogin();
        if (newLogin != null){
            User user = userService.findByLogin(newLogin);
            if (user != null){
                return new ResponseEntity<Object>("Пользователь " + newLogin + " уже существует",HttpStatus.BAD_REQUEST);
            }
            else{
                user = new User();
                user.setLogin(newLogin);
                user.setPassword(addNewUser.getPassword());
                userService.addUser(user);

                return new ResponseEntity<Object>("Добавлен новый пользователь", HttpStatus.OK);
            }
        }
        return new ResponseEntity<Object>("Нужен логин",HttpStatus.BAD_REQUEST);
    }

//    public ModelAndView addUser(@RequestParam String login, @RequestParam String password) {
//        if(userService.findByLogin(login)!=null){
//            return new ModelAndView("greeting", "loginExist", "Логин занят");
//        }
//
//        User n = new User();
//        n.setLogin(login);
//        n.setPassword(password);
//        n.setEmail(login+"@pm.ru");
//
//        userService.addUser(n);
//        return new ModelAndView("greeting", "userList", userService.getAllUser());
//    }

    @GetMapping (path = "/all")
    public ModelAndView getAllUsers(Principal principal) {
        ModelAndView model = new ModelAndView("/all");
        model.addObject("userList", userService.getAllUser());
        model.addObject("loggedUser", principal.getName());

        return model;
//        return new ModelAndView("all", "userList", userService.getAllUser());
    }
}
