package com.example.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    
    private AccountService accServ;
    private MessageService MessServ;
    MessageRepository messRepo;
    AccountRepository accRepo;
    public SocialMediaController(AccountService as,MessageService ms){
        this.MessServ = ms;
        this.accServ = as;
    }
    
    @PostMapping("/register")
    public ResponseEntity<Object> newUserAccountRegister(@RequestBody Account acc) {
        Account rAcc = accServ.getAccountByUsername(acc.getUsername());
        if(rAcc != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Account saveAcc=accServ.saveAccount(acc);
        return new ResponseEntity<>(saveAcc,HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> verifyUserLogin(@RequestBody Account account) {
        String strUser = accServ.getUserNameString(account);
        if (strUser != null && strUser.equals(account.getUsername())) {
            Account storedAcc = accServ.getAccountByUsername(account.getUsername());
    
            if (storedAcc != null && storedAcc.getPassword().equals(account.getPassword())) {
                return new ResponseEntity<>(storedAcc,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/messages")
    public ResponseEntity<Message> addNewMessageController(@RequestBody Message message){
        if((!message.getMessageText().isBlank()) && (message.getMessageText().length()<=255)){
            Message mess = MessServ.createNewMessageService(message);
            if(mess!=null)
                return new ResponseEntity<>(mess, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = MessServ.getAllMessages();

        if (messages.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK); 
        } else {
            return new ResponseEntity<>(messages, HttpStatus.OK); 
        }
    }
    @GetMapping("/messages/{message_id}")
    public Message getMsgById(@PathVariable int message_id){
       return MessServ.getMessageById(message_id);
    }
    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<Object> deletedMsgByMsgID(@PathVariable int message_id){
        int upRows = MessServ.deleteMessageById(message_id);
        if(upRows == 1) {
            return new ResponseEntity<>(upRows, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK); 
        }
    } 
    
    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<Integer> updateMsgByMsgId(@PathVariable int message_id, @RequestBody Message replacement){
        if((!replacement.getMessageText().isBlank()) && (replacement.getMessageText().length()<=255)){
            int i = MessServ.updateMessageById(message_id, replacement);
            if(i==1)
            return new ResponseEntity<>(i,HttpStatus.OK);              
    }
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
    }
    
    @GetMapping("accounts/{account_id}/messages")
    public List<Message> getMessagesOfaUser(@PathVariable int account_id){
        return MessServ.getMessagesOfaUser(account_id);
    }
}