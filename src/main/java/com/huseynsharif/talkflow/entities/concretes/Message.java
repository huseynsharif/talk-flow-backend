package com.huseynsharif.talkflow.entities.concretes;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

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
    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private User senderUserId;

    @NotNull
    @NotBlank
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room targetRoomId;


    @NotNull
    @NotBlank
    @JoinColumn(name = "message_text")
    private String messageText;

    @NotNull
    @NotBlank
    @JoinColumn(name = "sent_time")
    @CreationTimestamp
    private LocalDateTime sentTime;

    public Message(User senderUserId, Room targetRoomId, String messageText) {
        this.senderUserId = senderUserId;
        this.targetRoomId = targetRoomId;
        this.messageText = messageText;
    }
}
