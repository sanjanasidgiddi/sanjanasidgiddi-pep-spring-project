package com.example.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@ResponseBody
@RequestMapping
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    /* Register a new Account */
    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account newAccount) {
        // Logic to register a new account
        Optional<Account> opA = accountService.addAccount(newAccount);
        if (opA.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        else if (opA.get().getAccountId() == null)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(opA.get());
    }

    /* Login a new Account */
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account existingAcc) {
        // Logic to let a user login
        Optional<Account> opA = accountService.loginAccount(existingAcc);
        if (opA.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(opA.get());
    }

    /* Post a new Message */
    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message newMessage) {
        // Logic to Post a new Message
        Optional<Message> opMessage = messageService.postMessage(newMessage);
        if (opMessage.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(opMessage.get());
    }

    /* Retrieve all Messages */
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages() {
        List<Message> messageList = messageService.retrieveAllMessages();
        return ResponseEntity.status(HttpStatus.OK).body(messageList);
    }

    /* Retrieve a Message by its messageID */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessageByMessageId(@PathVariable Integer messageId) {
        Optional<Message> opMessage = messageService.findByMessageId(messageId);
        if (opMessage.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(opMessage.get());
    }

    /* Retrieve all Messages by a particular user */
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByParticularAccount(@PathVariable Integer accountId) {
        List<Message> messageList = messageService.getMessagesByParticularAccount(accountId);
        return ResponseEntity.status(HttpStatus.OK).body(messageList);
    }

    /* Delete a Message by its messageID */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageByMessageId(@PathVariable Integer messageId) {
        Optional<Integer> opInt = messageService.deleteByMessageId(messageId);
        if (opInt.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(opInt.get());
    }

    /* Update a Message by its messageID */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageByMessageId(@PathVariable Integer messageId, @RequestBody Map<String, String> msgText) {
        Optional<Integer> opInt = messageService.updateByMessageId(messageId, msgText.get("messageText"));
        if (opInt.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(opInt.get());
    }
}
