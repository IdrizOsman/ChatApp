package com.example.chatapp.controller;

import com.example.chatapp.model.Channel;
import com.example.chatapp.service.ChannelService;
import com.example.chatapp.service.UserService;
import com.example.chatapp.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.HashSet;

@RestController
@RequestMapping("/api/channels")
public class ChannelController {

    private final ChannelService channelService;
    private final UserService userService;

    public ChannelController(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<Channel> createChannel(@RequestParam String name, @RequestParam Set<Long> userIds) {
        // Логика за добавяне на потребители към канала
        Set<User> users = new HashSet<>();
        for (Long userId : userIds) {
            User user = userService.getUserById(userId);  // Трябва да имаш метод в UserService за извличане на потребител по ID
            if (user != null) {
                users.add(user);
            }
        }
        Channel newChannel = new Channel(name, users);
        return ResponseEntity.ok(channelService.createChannel(newChannel));
    }

    @GetMapping
    public ResponseEntity<Set<Channel>> getAllChannels() {
        return ResponseEntity.ok(channelService.getAllChannels());
    }

    @DeleteMapping("/{channelId}")
public ResponseEntity<Void> deleteChannel(@PathVariable Long channelId) {
    try {
        channelService.deleteChannel(channelId);
        return ResponseEntity.noContent().build();  // 204 статус за успешно изтриване
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 статус
    }
}
}
