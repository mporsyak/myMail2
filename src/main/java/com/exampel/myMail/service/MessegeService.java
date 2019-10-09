package com.exampel.myMail.service;


import com.exampel.myMail.model.MessageDto;
import com.exampel.myMail.model.Messege;
import com.exampel.myMail.model.User;
import com.exampel.myMail.repository.MessegeRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MessegeService {
    @Autowired
    MessegeRepository messegeRepository;

    private RestTemplate restTemplate;

    public MessegeService(RestTemplateBuilder templateBuilder){
        restTemplate = templateBuilder.build();
    }

    public void addMessege(Messege messege){messegeRepository.save(messege);}

    public Messege getMessege(String content){return messegeRepository.findByContent(content);}

    private HttpEntity<String> getEntity(User user){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);

        String auth = user.getLogin() + ":" + user.getPassword();
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        headers.set("Authorization", authHeader);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        return entity;
    }

    public String getAllIncomeMessagesWithTemplate(User user){
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/server/showIncomeMessages/" + user.getLogin(), HttpMethod.GET, getEntity(user), String.class);
        return response.getBody();
    }

    public String getAllOutcomeMessagesWithTemplate(User user){
        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8080/server/showOutcomeMessages/" + user.getLogin(), HttpMethod.GET, getEntity(user), String.class);
        return response.getBody();
    }

    public List<MessageDto> getAllIncomeMessages(String authUserLogin){
        return getAllDirectMessages(false, authUserLogin);
    }

    public List<MessageDto> getAllOutcomeMessages(String authUserLogin){
        return getAllDirectMessages(true, authUserLogin);
    }

    private List<MessageDto> getAllDirectMessages(boolean isOutcomeDirect, String authUserLogin){
        List<Messege> allMessegeList = getAllMessage();

        List<MessageDto> messages = new ArrayList();
        for (int i = 0; i < allMessegeList.size(); i++) {
            Messege currentMessege = allMessegeList.get(i);

            if (isOutcomeDirect ? currentMessege.getUserSender().getLogin().equals(authUserLogin) : currentMessege.getUserRecip().getLogin().equals(authUserLogin)){
                MessageDto messageInfo = new MessageDto();
                messageInfo.setContent(currentMessege.getContent());
                messageInfo.setGoal((isOutcomeDirect ? ("Получатель: " + currentMessege.getUserRecip().getLogin()) : ("Отправитель: " + currentMessege.getUserSender().getLogin())));
                messages.add(messageInfo);
            }
        }

        return messages;
    }

    public List<Messege> getAllMessage(){
        return (List<Messege>)messegeRepository.findAll();
    }

    public List<Messege> getAllMessageByUser(User userSender, User userRecip){
        return messegeRepository.findByUserSenderAndUserRecip(userSender, userRecip);
    }

}
