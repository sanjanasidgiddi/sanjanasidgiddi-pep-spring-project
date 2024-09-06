package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    /* Retrieving list of messages by a particular user */
    List<Message> findAllBypostedBy(@Param("postedBy") Integer postedBy);
}
