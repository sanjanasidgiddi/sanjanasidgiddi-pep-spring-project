package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Transactional
@Service
public class MessageService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;
    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository){
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Persist a new message entity - Post.
     * @param message a new message entity.
     * @return the persisted message entity.
     */
    public Optional<Message> postMessage(Message message){
        /* Boolean checks to ensure valid message format */
        if ((accountRepository.findById(message.getPostedBy()).isPresent()) 
            && (message.getMessageText().length() <= 255)
            && (!"".equals(message.getMessageText())))
            return Optional.of(messageRepository.save(message));
        return Optional.empty();
    }

    /**
     * Retrieve all message entites.
     * @return the list of message entities.
     */
    public List<Message> retrieveAllMessages(){
        return messageRepository.findAll();
    }

    /**
     * Retrieve a message by its id.
     * @return the optionalmessage entity.
     */
    public Optional<Message> findByMessageId(Integer mid){
        return messageRepository.findById(mid);
    }

    /**
     * Retrieve a message by its account or user id.
     * @return the optionalmessage entity.
     */
    public List<Message> getMessagesByParticularAccount(Integer accountId){
        return messageRepository.findAllBypostedBy(accountId);
    }

    /**
     * Delete a message by its id.
     * @return the optionalint number of rows updated.
     */
    public Optional<Integer> deleteByMessageId(Integer mid){
        Optional<Message> m = messageRepository.findById(mid);
        if (m.isPresent()) {
            messageRepository.deleteById(mid);
            return Optional.of(1);
        }
        return Optional.empty();
    }

    /**
     * Update a message by its id.
     * @return the optionalint number of rows updated.
     */
    public Optional<Integer> updateByMessageId(Integer mid, String newText){
        Optional<Message> m = messageRepository.findById(mid);
        if (m.isPresent() && !"".equals(newText) && (newText.length() > 0) && (newText.length() <= 255)) {
            m.get().setMessageText(newText);
            messageRepository.save(m.get());
            System.out.println("GIVEN TEXT NEW MSG --> " + newText);
            return Optional.of(1);
        }
        return Optional.empty();
    }
}
