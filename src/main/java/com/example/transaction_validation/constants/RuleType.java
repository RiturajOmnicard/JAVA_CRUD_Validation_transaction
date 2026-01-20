package com.example.transaction_validation.constants;

public enum RuleType {
    MAX_AMOUNT,
    MIN_AMOUNT,
    DAILY_TXN_COUNT,
    DAILY_TXN_AMOUNT,
    COOLING_PERIOD,
    USER_STATUS_CHECK,
    CHANNEL_LIMIT,
    TIME_WINDOW_START_HOUR,
    TIME_WINDOW_END_HOUR,
    TIME_WINDOW,
    HIGH_VELOCITY,
    BLACKLISTED_USER
}
