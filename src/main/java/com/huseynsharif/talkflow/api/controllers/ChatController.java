package com.huseynsharif.talkflow.api.controllers;

import com.huseynsharif.talkflow.entities.concretes.dtos.TestMessage;
import lombok.AllArgsConstructor;
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
    // @SendTo("/topic/message")
    public TestMessage receiveMessage(@Payload TestMessage message){
        System.out.println(message);
        simpMessagingTemplate.convertAndSend("/topic/message", message);
        return message;
    }

    @MessageMapping("/private-message")
    public TestMessage recMessage(@Payload TestMessage message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }


}
