package com.example.chatapp.service;

import com.example.chatapp.model.Message;

import java.util.List;

public interface MessageService {
    Message sendMessageToUser(Long senderId, Long receiverId, Message message);
    public Message sendMessageToChannel(Long channelId, Message message);
    List<Message> getMessagesBySender(Long senderId);
    List<Message> getMessagesByReceiver(Long receiverId);
    List<Message> getMessagesByChannel(Long channelId);
    Message saveMessage(Message message);
    void deleteChannel(Long channelId);
}
