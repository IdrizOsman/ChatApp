package com.example.chatapp.service.impl;

import com.example.chatapp.model.Channel;
import com.example.chatapp.model.Message;
import com.example.chatapp.model.User;
import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.repository.ChannelRepository;
import com.example.chatapp.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository, ChannelRepository channelRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
    }

    @Override
public Message sendMessageToUser(Long senderId, Long receiverId, Message message) {
    if (message == null || message.getContent() == null) {
        throw new IllegalArgumentException("Съобщението не може да бъде празно!");
    }

    System.out.println("Получено съобщение: " + message.getContent());

    User sender = userRepository.findById(senderId)
    .orElseThrow(() -> {
        System.out.println("Sender with ID " + senderId + " not found!");
        return new RuntimeException("Sender not found");
    });

User receiver = userRepository.findById(receiverId)
    .orElseThrow(() -> {
        System.out.println("Receiver with ID " + receiverId + " not found!");
        return new RuntimeException("Receiver not found");
    });

    message.setSender(sender);
    message.setReceiver(receiver);

    return messageRepository.save(message);
}

@Override
public void deleteChannel(Long channelId) {
    Optional<Channel> channel = channelRepository.findById(channelId);
    
    if (channel.isPresent()) {
        channelRepository.delete(channel.get());
    } else {
        throw new IllegalArgumentException("Channel not found with ID: " + channelId);
    }
}

@Override
public Message saveMessage(Message message) {
    // Save the message and return it
    return messageRepository.save(message);
}

@Override
public Message sendMessageToChannel(Long channelId, Message message) {
    Channel channel = channelRepository.findById(channelId)
            .orElseThrow(() -> new RuntimeException("Каналът не е намерен"));

    // Ако съобщението е за канал, не се задава получател
    message.setReceiver(null);  
    message.setChannel(channel);

    return messageRepository.save(message);
}

    @Override
    public List<Message> getMessagesBySender(Long senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    @Override
    public List<Message> getMessagesByReceiver(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }

    @Override
    public List<Message> getMessagesByChannel(Long channelId) {
        return messageRepository.findByChannelId(channelId);
    }
}
