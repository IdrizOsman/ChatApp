package com.example.chatapp.controller;

import com.example.chatapp.model.Message;
import com.example.chatapp.model.User;
import com.example.chatapp.model.Channel;
import com.example.chatapp.service.MessageService;
import com.example.chatapp.dto.MessageDTO;
import com.example.chatapp.service.ChannelService;
import com.example.chatapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final ChannelService channelService;
    private final UserService userService;
    private final MessageService messageService;

    public MessageController(MessageService messageService, ChannelService channelService, UserService userService) {
        this.messageService = messageService;
        this.channelService = channelService;
        this.userService = userService;
    }

    // Updated method to send message to a user, accepting senderId in the URL and message details in the body
    @PostMapping("/user/{senderId}/{receiverId}")
public ResponseEntity<Message> sendMessageToUser(
    @PathVariable Long senderId,
    @PathVariable Long receiverId,
    @RequestBody Message message) {
    // Get sender and receiver from userService
    User sender = userService.getUserById(senderId);
    User receiver = userService.getUserById(receiverId);

    if (sender == null || receiver == null) {
        return ResponseEntity.badRequest().body(null);  // Or handle it differently
    }

    message.setSender(sender);
    message.setReceiver(receiver);
    message.setContent(message.getContent());  // Make sure content is set
    messageService.saveMessage(message);

    return ResponseEntity.ok(message);
}

    // Updated method to send message to a channel, accepting senderId in the message DTO
    @PostMapping("/channel/{channelId}")
    public ResponseEntity<Message> sendMessageToChannel(@PathVariable Long channelId, @RequestBody MessageDTO messageDTO) {
        User sender = userService.getUserById(messageDTO.getSenderId()); // Ensure user exists
        if (sender == null) {
            return ResponseEntity.badRequest().body(null);
        }
    
        Channel channel = channelService.getChannelById(channelId);
        if (channel == null) {
            return ResponseEntity.badRequest().body(null);
        }
    
        // Check if the sender is authorized to send a message to the channel (optional)
        // e.g. Ensure sender is part of the channel users if needed
    
        Message message = new Message();
        message.setSender(sender);
        message.setContent(messageDTO.getContent());
        message.setChannel(channel);
    
        // Save message to database
        messageService.saveMessage(message);
    
        return ResponseEntity.ok(message);
    }

    // Get messages by sender
    @GetMapping("/sender/{senderId}")
    public ResponseEntity<List<Message>> getMessagesBySender(@PathVariable Long senderId) {
        return ResponseEntity.ok(messageService.getMessagesBySender(senderId));
    }

    // Get messages by receiver
    @GetMapping("/receiver/{receiverId}")
    public ResponseEntity<List<Message>> getMessagesByReceiver(@PathVariable Long receiverId) {
        return ResponseEntity.ok(messageService.getMessagesByReceiver(receiverId));
    }

    // Get messages by channel
    @GetMapping("/channel/{channelId}")
    public ResponseEntity<List<Message>> getMessagesByChannel(@PathVariable Long channelId) {
        return ResponseEntity.ok(messageService.getMessagesByChannel(channelId));
    }


    
}
