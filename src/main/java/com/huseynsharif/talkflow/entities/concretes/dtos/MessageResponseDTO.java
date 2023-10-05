package com.huseynsharif.talkflow.entities.concretes.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponseDTO {

    private String senderName;

    private String targetRoomName;

    private String messageText;

}
