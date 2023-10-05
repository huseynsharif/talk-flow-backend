package com.huseynsharif.talkflow.dataAccess.abstracts;

import com.huseynsharif.talkflow.entities.concretes.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageDAO extends JpaRepository<Message, Integer> {

    @Query("from Message where targetRoomId.id=:id")
    List<Message> getAllByTargetRoomId(int id);

}
