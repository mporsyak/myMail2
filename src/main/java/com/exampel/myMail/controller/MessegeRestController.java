package com.exampel.myMail.controller;

import com.exampel.myMail.model.MessageDto;
import com.exampel.myMail.model.Messege;
import com.exampel.myMail.model.SendMessage;
import com.exampel.myMail.model.User;
import com.exampel.myMail.service.MessegeService;
import com.exampel.myMail.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class MessegeRestController {

    @Autowired
   private MessegeService messegeService;

    @Autowired
   private UserService userService;

    @PostMapping(value = "/sendMessage")
    public ResponseEntity<?> sendMessage(Principal principal,
                             @RequestBody SendMessage sendMessage
    ) {
        User userRecipient = userService.findByLogin(sendMessage.getMyRecipLogin());
        User userSender = userService.findByLogin(principal.getName());

        if(userRecipient != null){
            Messege messege = new Messege();
            messege.setContent(sendMessage.getMyContent());
            messege.setUserSender(userSender);
            messege.setUserRecip(userRecipient);
            messege.setMsgTime(new Date());
            messegeService.addMessege(messege);

            return new ResponseEntity<>("Сообщение успешно отправлено", HttpStatus.OK);
        }

        return new ResponseEntity<>("Пользователь " + sendMessage.getMyRecipLogin() + " не найден", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/showIncomeMessages")
    public List<MessageDto> showIncomeMessages(Principal principal){
        return messegeService.getAllIncomeMessages(principal.getName());
    }

    @GetMapping(value = "/showOutcomeMessages")
    public List<MessageDto> showOutcomeMessages(Principal principal){
        return messegeService.getAllOutcomeMessages(principal.getName());
    }

    /*@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
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
    }*/
}
