package com.exampel.myMail.controller;

import com.exampel.myMail.model.Messege;
import com.exampel.myMail.service.MessegeService;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
@Controller
public class ShowMessege {

    @Autowired
    MessegeService messegeService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/showUserMessege", method = RequestMethod.GET)
    public ModelAndView addUserProfile(Principal principal){

        String authUserLogin = principal.getName();
        List<Messege> allMessegeList = messegeService.getAllMessage();
        List<Messege> authListMessege = new ArrayList();
        for (int i = 0; i < allMessegeList.size(); i++) {
            Messege currentMessege = allMessegeList.get(i);
            if(currentMessege.getUserSender().getLogin().equals(authUserLogin) ||
                    currentMessege.getUserRecip().getLogin().equals(authUserLogin)){
                authListMessege.add(currentMessege);
            }
        }
        ModelAndView model = new ModelAndView("/allMessege");
        model.addObject("messegeList", authListMessege);
        model.addObject("authUserLogin", authUserLogin);

        return model;
    }
}
