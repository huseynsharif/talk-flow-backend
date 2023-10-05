package com.huseynsharif.talkflow.business.abstracts;

import com.huseynsharif.talkflow.core.utilities.results.DataResult;
import com.huseynsharif.talkflow.entities.concretes.Message;
import com.huseynsharif.talkflow.entities.concretes.dtos.MessageInputDTO;
import com.huseynsharif.talkflow.entities.concretes.dtos.MessageResponseDTO;

import java.util.List;

public interface MessageService {

    DataResult<List<MessageResponseDTO>> getAllByTargetRoomId(int id);

    DataResult<Message> sendMessage(MessageInputDTO messageInputDTO);
}
