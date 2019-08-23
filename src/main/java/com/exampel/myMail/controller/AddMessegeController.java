package com.exampel.myMail.controller;

import com.exampel.myMail.model.Messege;
import com.exampel.myMail.model.User;
import com.exampel.myMail.service.MessegeService;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.security.Principal;
@Controller
public class AddMessegeController {

    @Autowired
   MessegeService messegeService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/addMessege", method = RequestMethod.GET)
    public ModelAndView addUserProfile(Principal principal){

        return new ModelAndView("/addMessege", "authUser", principal.getName());
    }

    @RequestMapping(value = "/addMessege", method = RequestMethod.POST)
    public String addUserProfile(@RequestParam(required = true) String authUserLogin,
                                       @RequestParam(required = true) String content,
                                       @RequestParam(required = true) String recipientLogin
    ) {

        User userRecipient = userService.findByLogin(recipientLogin);
        User userSender = userService.findByLogin(authUserLogin);

        if(userRecipient!=null){
            Messege messege = new Messege();
            messege.setContent(content);
            messege.setUserSender(userSender);
            messege.setUserRecip(userRecipient);
            messegeService.addMessege(messege);
        }
        return "redirect:/showUserMessege";
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public String sendMessage(@RequestParam(required = true) String userSenderLogin,
                              @RequestParam(required = true) String userRecipLogin,
                             @RequestParam(required = true) String messageContent) {
        User userRecipient = userService.findByLogin(userRecipLogin);
        User userSender = userService.findByLogin(userSenderLogin);

        Messege messege = new Messege();
        messege.setContent(messageContent);
        messege.setUserSender(userSender);
        messege.setUserRecip(userRecipient);
        messegeService.addMessege(messege);

        return "redirect:/all";
    }
}
