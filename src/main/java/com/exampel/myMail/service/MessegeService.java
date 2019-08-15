package com.exampel.myMail.service;


import com.exampel.myMail.model.Messege;
import com.exampel.myMail.repository.MessegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessegeService {
    @Autowired
    MessegeRepository messegeRepository;

    public void addMessege(Messege messege){messegeRepository.save(messege);}

    public Messege getMessege(String content){return messegeRepository.findByContent(content);}

    public List<Messege> getAllMessage(){
        return (List<Messege>)messegeRepository.findAll();
    }



}
