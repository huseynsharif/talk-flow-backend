package com.huseynsharif.talkflow.business.concretes;

import com.huseynsharif.talkflow.business.abstracts.RoomService;
import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.core.utilities.results.ErrorDataResult;
import com.huseynsharif.talkflow.core.utilities.results.SuccessDataResult;
import com.huseynsharif.talkflow.dataAccess.abstracts.RoomDAO;
import com.huseynsharif.talkflow.entities.concretes.Room;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomManager implements RoomService {

    private RoomDAO roomDAO;

    @Override
    public DataResult<List<Room>> getAll() {
        return new SuccessDataResult<>(this.roomDAO.findAll(), "Successfully listed");
    }

    @Override
    public DataResult<Room> add(Room room) {
        return new SuccessDataResult<>(this.roomDAO.save(room), "Successfully saved");
    }

    @Override
    public DataResult<Room> findById(int roomId) {

        Room room = this.roomDAO.findById(roomId).orElse(null);

        if (room == null){
            return new ErrorDataResult<>("Cannot find room with roomId: " + roomId);
        }

        return new SuccessDataResult<>(room , "Successfully finded");
    }

    @Override
    public DataResult<Integer> findRoomIdByRoomName(String roomName) {
        Room room = this.roomDAO.findRoomByRoomName(roomName);

        if (room == null){
            Room newRoom = this.roomDAO.save(new Room(roomName));
            return new SuccessDataResult<>(newRoom.getId() , "Successfully created");
        }

        return new SuccessDataResult<>(room.getId() , "Successfully finded");
    }
}
