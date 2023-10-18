package com.huseynsharif.talkflow.business.concretes;

import com.huseynsharif.talkflow.business.abstracts.MessageService;
import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.core.utilities.results.ErrorDataResult;
import com.huseynsharif.talkflow.core.utilities.results.SuccessDataResult;
import com.huseynsharif.talkflow.dataAccess.abstracts.MessageDAO;
import com.huseynsharif.talkflow.dataAccess.abstracts.RoomDAO;
import com.huseynsharif.talkflow.dataAccess.abstracts.UserDAO;
import com.huseynsharif.talkflow.entities.concretes.Message;
import com.huseynsharif.talkflow.entities.concretes.Room;
import com.huseynsharif.talkflow.entities.concretes.User;
import com.huseynsharif.talkflow.entities.concretes.dtos.MessageInputDTO;
import com.huseynsharif.talkflow.entities.concretes.dtos.MessageResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageManager implements MessageService {

    private MessageDAO messageDAO;
    private UserDAO userDAO;
    private RoomDAO roomDAO;
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public DataResult<List<MessageResponseDTO>> getAllByTargetRoomId(int id) {

        List<Message> oldMessages = this.messageDAO.getAllByTargetRoomId(id);

        if (oldMessages.isEmpty()) {
            return new ErrorDataResult<>("Cannot find any message with given roomId: " + id);
        }

        List<MessageResponseDTO> response = oldMessages
                .stream()
                .map((message) -> new MessageResponseDTO(
                        message.getSenderUserId().getUsername(),
                        message.getTargetRoomId().getRoomName(),
                        message.getMessageText()
                )).toList();

        return new SuccessDataResult<>(response, "Successfully listed.");
    }

    @Override
    public DataResult<Message> sendMessage(MessageInputDTO messageInputDTO) {

        User senderUser = this.userDAO.findById(messageInputDTO.getSenderId()).orElse(null);
        Room targetRoom = this.roomDAO.findById(messageInputDTO.getTargetRoomId()).orElse(null);

        Message message = new Message(
                senderUser,
                targetRoom,
                messageInputDTO.getMessage()
        );
        MessageResponseDTO messageResponseDTO = new MessageResponseDTO(
                senderUser.getUsername(),
                targetRoom.getRoomName(),
                messageInputDTO.getMessage()
        );
        simpMessagingTemplate.convertAndSend("/topic/message/" + targetRoom.getRoomName(), messageResponseDTO);
        messageDAO.save(message);
        return new SuccessDataResult<>(this.messageDAO.save(message), "Successfully saved.");
    }
}
