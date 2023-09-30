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
    private String senderName;

    @NotNull
    @NotBlank
    private String message;

    @NotNull
    @NotBlank
    private String roomName;

}