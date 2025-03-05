package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
@Transactional
public class MessageService {
    MessageRepository mRepo;
    AccountRepository aRepo;
    AccountService accountService;
    public MessageService(MessageRepository mRepo, AccountRepository aRepo){
        this.mRepo = mRepo;
        this.aRepo = aRepo;
    }
    
   public Message createNewMessageService(Message message){
        Optional<Account> messageInAccountOptional = aRepo.findById(message.getPostedBy());
        if(messageInAccountOptional.isPresent())
            return mRepo.save(message);
        return null;
    }
    
    public Message saveMessage(Message message){
       return mRepo.save(message);
    }


    
    public int deleteMessageById(int message_id){
        Optional<Message> existingMessage = mRepo.findById(message_id);
        if(existingMessage.isPresent()){
            mRepo.deleteById(message_id);
            return 1;
        }
        return 0;
    }
    
    public Message getMessageById(int message_id) {
        Optional<Message> existingMess = mRepo.findById(message_id);
        if(existingMess.isPresent()){
            return existingMess.get();
        }
        return null;
    }
    
    public List<Message> getAllMessages(){
        return mRepo.findAll();
    }
   
    public int updateMessageById(int message_id, Message replacement){
        Optional<Message> messageOptional = mRepo.findById(message_id);
        if((messageOptional.isPresent())){
            Message message = messageOptional.get();
            message.setMessageText(replacement.getMessageText());
            return 1;            
        }
        return 0;
    }
    
    public List<Message> getMessagesOfaUser(int accountId){
        Optional<List<Message>> listOptional = mRepo.getPostedBy(accountId);
        if(listOptional.isPresent()){
            return listOptional.get();
        }
        return new ArrayList<>();
    }
}