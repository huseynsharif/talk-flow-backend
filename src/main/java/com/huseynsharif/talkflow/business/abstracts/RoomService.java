package com.huseynsharif.talkflow.business.abstracts;

import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.entities.concretes.Room;

import java.util.List;

public interface RoomService {

    DataResult<List<Room>> getAll();

    DataResult<Room> add(Room room);

    DataResult<Room> findById(int roomId);

    DataResult<Integer> findRoomIdByRoomName(String roomName);



}
