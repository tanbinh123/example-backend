package me.vrnsky.example.service;

import me.vrnsky.example.model.Message;
import me.vrnsky.example.persistence.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> messages() throws Exception {
        return messageRepository.listMessages();
    }

    public Message addMessage(String text) throws Exception {
        return messageRepository.addMessage(text);
    }
}
