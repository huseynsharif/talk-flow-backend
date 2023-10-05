package com.huseynsharif.talkflow.entities.concretes.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageInputDTO {

    @NotNull
    @NotBlank
    private int senderId;

    @NotNull
    @NotBlank
    private int targetRoomId;

    @NotNull
    @NotBlank
    private String message;

}