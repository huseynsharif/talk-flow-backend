package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.business.abstracts.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@AllArgsConstructor
@CrossOrigin
public class RoomController {

    private RoomService roomService;

    @GetMapping("/find-by-id")
    public ResponseEntity<?> findById(@RequestParam int roomId){
        return ResponseEntity.ok(this.roomService.findById(roomId));
    }

    @GetMapping("/find-roomId-by-room-name")
    public ResponseEntity<?> findRoomIdByRoomName(@RequestParam String roomName){
        System.out.println("salam");
        return ResponseEntity.ok(this.roomService.findRoomIdByRoomName(roomName));
    }


}
