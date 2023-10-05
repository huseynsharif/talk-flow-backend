package com.huseynsharif.talkflow.api.controllers;


import com.huseynsharif.talkflow.business.abstracts.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin
@RequestMapping("/api/messages")
public class MessageController {

    private MessageService messageService;

    @GetMapping("/getall-by-roomid")
    public ResponseEntity<?> getAllByRoomId(@RequestParam int id){
        return ResponseEntity.ok(this.messageService.getAllByTargetRoomId(id));
    }


}
