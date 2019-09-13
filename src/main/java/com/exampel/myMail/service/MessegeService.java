package com.exampel.myMail.service;


import com.exampel.myMail.model.MessageDto;
import com.exampel.myMail.model.Messege;
import com.exampel.myMail.repository.MessegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessegeService {
    @Autowired
    MessegeRepository messegeRepository;

    public void addMessege(Messege messege){messegeRepository.save(messege);}

    public Messege getMessege(String content){return messegeRepository.findByContent(content);}

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



}
