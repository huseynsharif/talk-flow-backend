package com.huseynsharif.talkflow.dataAccess.abstracts;

import com.huseynsharif.talkflow.entities.concretes.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomDAO extends JpaRepository<Room, Integer> {

    Room findRoomByRoomName(String roomName);

}
