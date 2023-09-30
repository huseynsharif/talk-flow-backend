package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.entities.concretes.dtos.MessageInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ChatController {


    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    //@SendTo("/topic")
    public MessageInputDTO receiveMessage(@Payload MessageInputDTO message){
        System.out.println(message);
        simpMessagingTemplate.convertAndSend("/topic/message/" + message.getRoomName(), message);
        return message;
    }

}
