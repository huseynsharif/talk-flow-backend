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
@Table(name = "chats")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id")
    private int id;

    @NotNull
    @NotBlank
    @OneToOne
    @JoinColumn(name = "user1_id")
    private User user1;

    @NotNull
    @NotBlank
    @OneToOne
    @JoinColumn(name = "user2_id")
    private User user2;

    @NotNull
    @NotBlank
    @Column(name = "created_at") // olmasa creationtimestamp qoy
    private LocalDateTime createdAt;

    @NotNull
    @NotBlank
    @Column(name = "last_message")
    private LocalDateTime lastMessage;

}
