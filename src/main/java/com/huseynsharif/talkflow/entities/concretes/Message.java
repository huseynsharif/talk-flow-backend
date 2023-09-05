package com.huseynsharif.talkflow.entities.concretes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int id;

    @NotNull
    @NotBlank
    @OneToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @NotNull
    @NotBlank
    @OneToOne
    @JoinColumn(name = "to_user_id")
    private User toUser;

    @NotNull
    @NotBlank
    @JoinColumn(name = "message_text")
    private String messageText;

    @NotNull
    @NotBlank
    @JoinColumn(name = "sent_time")
    private LocalDateTime sentTime;



}
