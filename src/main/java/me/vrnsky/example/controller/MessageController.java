package me.vrnsky.example.controller;

import me.vrnsky.example.model.Message;
import me.vrnsky.example.model.MessageData;
import me.vrnsky.example.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MessageController {

    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/api/messages")
    @ResponseBody
    public ResponseEntity<List<Message>> list() throws Exception {
        return ResponseEntity.ok(messageService.messages());
    }

    @PostMapping("/api/messages")
    public ResponseEntity<Message> message(@RequestBody MessageData message) throws Exception {
        return ResponseEntity.ok(messageService.addMessage(message.getText()));
    }
}
