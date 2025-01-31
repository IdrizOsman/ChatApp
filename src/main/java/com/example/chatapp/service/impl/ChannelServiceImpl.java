package com.example.chatapp.service.impl;

import com.example.chatapp.model.Channel;
import com.example.chatapp.repository.ChannelRepository;
import com.example.chatapp.service.ChannelService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;

@Service
public class ChannelServiceImpl implements ChannelService {

    private ChannelRepository channelRepository;

    public ChannelServiceImpl(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }

    @Override
    public Channel createChannel(Channel channel) {
        return channelRepository.save(channel);
    }

    @Override
    public Set<Channel> getAllChannels() {
        return new HashSet<>(channelRepository.findAll());
    }

    @Override
    public void deleteChannel(Long channelId) {
        Optional<Channel> channel = channelRepository.findById(channelId);
        
        if (channel.isPresent()) {
            // If the channel is found, delete it
            channelRepository.delete(channel.get());
        } else {
            throw new IllegalArgumentException("Channel not found with ID: " + channelId);
        }
    }

    @Override
public Channel getChannelById(Long channelId) {
    return channelRepository.findById(channelId)
            .orElseThrow(() -> new EntityNotFoundException("Каналът не беше намерен с ID: " + channelId));
}
}
