package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer>{

        //@Query("DELETE FROM Message m WHERE m.id = ?1")
        //int deleteMsgById(Integer id);

        @Query("SELECT m FROM Message m WHERE m.postedBy = ?1")
        Optional<List<Message>> getPostedBy(Integer postedBy);

        //boolean existsById(Integer postedBy);


}