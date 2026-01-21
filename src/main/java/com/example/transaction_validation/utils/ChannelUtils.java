package com.example.transaction_validation.utils;

import com.example.transaction_validation.constants.Channel;
import com.example.transaction_validation.exception.BusinessException;

public class ChannelUtils {

    private ChannelUtils() {}

    public static Channel parseChannel(String channel) {
        if (channel == null || channel.trim().isEmpty()) {
            throw new BusinessException("INVALID_CHANNEL", "Channel cannot be empty");
        }

        try {
            return Channel.valueOf(channel.trim().toUpperCase());
        } catch (Exception e) {
            throw new BusinessException("INVALID_CHANNEL", "Invalid channel: " + channel);
        }
    }
}
