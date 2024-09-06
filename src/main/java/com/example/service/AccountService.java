package com.example.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Transactional
@Service
public class AccountService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;
    @Autowired
    public AccountService(AccountRepository accountRepository, MessageRepository messageRepository){
        this.accountRepository = accountRepository;
        this.messageRepository = messageRepository;;
    }

    /**
     * Persist a new account entity - Registration.
     * @param account a transient account entity.
     * @return the persisted account entity.
     */
    public Optional<Account> addAccount(Account account){
        /* Duplicate username found */
        if (accountRepository.findByUsername(account.getUsername()) != null)
            return Optional.of(new Account());
        /*  Invalid username/password */
        if ("".equals(account.getUsername()) || account.getPassword().length() < 4)
            return Optional.empty();
        return Optional.of(accountRepository.save(account));
    }

    /**
     * Login an existing account entity - LOGIN.
     * @param account an existing account entity.
     * @return the logged in account entity.
     */
    public Optional<Account> loginAccount(Account account){
        /* Account found, login proceeds */
        if (accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()) != null)
            return Optional.of(accountRepository.findByUsernameAndPassword(account.getUsername(), account.getPassword()));
        return Optional.empty();
    }
}
