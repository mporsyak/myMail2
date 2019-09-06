package com.exampel.myMail.controller;

import com.exampel.myMail.model.MessageInfo;
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
import java.util.List;

@RestController
@RequestMapping("/api")
public class MessegeController {

    @Autowired
   MessegeService messegeService;

    @Autowired
    UserService userService;


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
            messegeService.addMessege(messege);

            return new ResponseEntity<>("Сообщение успешно отправлено", HttpStatus.OK);
        }

        return new ResponseEntity<>("Пользователь " + sendMessage.getMyRecipLogin() + " не найден", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/showMessages")
    public List<MessageInfo> addUserProfile(Principal principal){

        String authUserLogin = principal.getName();
        List<Messege> allMessegeList = messegeService.getAllMessage();

        List<MessageInfo> messages = new ArrayList();
        for (int i = 0; i < allMessegeList.size(); i++) {
            Messege currentMessege = allMessegeList.get(i);

            if(currentMessege.getUserSender().getLogin().equals(authUserLogin) || currentMessege.getUserRecip().getLogin().equals(authUserLogin)){
                MessageInfo messageInfo = new MessageInfo();
                messageInfo.setName(currentMessege.getUserSender().getLogin().equals(principal.getName()) ? "Исходящее" : "Входящее");
                messageInfo.setEmail(currentMessege.getContent());
                messageInfo.setGoal((currentMessege.getUserSender().getLogin().equals(principal.getName()) ?
                        "Получатель: " : "Отправитель: ") + currentMessege.getUserRecip().getLogin());
                messages.add(messageInfo);
            }
        }

        return messages;
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
