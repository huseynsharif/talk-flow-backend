package com.huseynsharif.talkflow.entities.concretes.dtos;


import ch.qos.logback.core.status.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TestMessage {

    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;

}
