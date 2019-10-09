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
public class ClientMessegeRestController {

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

    @GetMapping(value = "client/showIncomeMessages")
    public String showIncomeMessagesWithTemplate(Principal principal){
        User user = userService.findByLogin(principal.getName());
        return messegeService.getAllIncomeMessagesWithTemplate(user);
    }

    @GetMapping(value = "client/showOutcomeMessages")
    public String showOutcomeMessagesWithTemplate(Principal principal){
        User user = userService.findByLogin(principal.getName());
        return messegeService.getAllOutcomeMessagesWithTemplate(user);
    }

    @GetMapping (path = "/allMessages/{user}")
    public List<MessageDto> allMessages(@PathVariable String user, Principal principal) {
        User userSender = userService.findByLogin(principal.getName());
        User userRecip = userService.findByLogin(user);

        List<Messege> senderMessages = messegeService.getAllMessageByUser(userSender, userRecip);
        List<Messege> recipMessages = messegeService.getAllMessageByUser(userRecip, userSender);

        List<MessageDto> result = new ArrayList<>();

        for (Messege messege : senderMessages){
            MessageDto  messageDto = new MessageDto();
            messageDto.setContent(messege.getContent());
            messageDto.setMyMsg(true);
            messageDto.setCreateMsgTime(messege.getMsgTime());

            result.add(messageDto);
        }

        for (Messege messege : recipMessages){
            MessageDto  messageDto = new MessageDto();
            messageDto.setContent(messege.getContent());
            messageDto.setMyMsg(false);
            messageDto.setCreateMsgTime(messege.getMsgTime());

            result.add(messageDto);
        }

        result.sort((a,b) -> a.getCreateMsgTime().compareTo(b.getCreateMsgTime()));
        return result;
    }
}
