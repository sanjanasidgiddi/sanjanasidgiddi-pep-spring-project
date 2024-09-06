package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /* Custom Query to find an account by username */
    Account findByUsername(String username);

    /* Custom Query to find an account by username and password */
    Account findByUsernameAndPassword(String username, String password);
}
