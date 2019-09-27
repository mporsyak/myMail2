package com.exampel.myMail.repository;

import com.exampel.myMail.model.Messege;
import com.exampel.myMail.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessegeRepository extends CrudRepository<Messege, Long>{
    Messege findByContent(String content);
    List<Messege> findByUserSenderAndUserRecip(User userSender, User userRecip);
}