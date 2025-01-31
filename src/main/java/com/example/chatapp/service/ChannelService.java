package com.example.chatapp.service;

import com.example.chatapp.model.Channel;

import java.util.Set;

public interface ChannelService {
    Channel createChannel(Channel channel);
    Set<Channel> getAllChannels();
    void deleteChannel(Long channelId);  // Добавяме метод за изтриване
    Channel getChannelById(Long channelId);

}
