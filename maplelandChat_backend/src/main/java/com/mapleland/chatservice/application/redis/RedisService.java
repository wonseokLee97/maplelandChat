package com.mapleland.chatservice.application.redis;

import java.time.Duration;

public interface RedisService {
    String get(String key);
    boolean exists(String key);
    Long decreaseAndDeleteIfZero(String key);
    long getTTLSec(String key);
    long getTTLMin(String key);
    void set(String key, String value, Duration duration);
    long increment(String key);
    long decrement(String key);
    void expire(String key, Duration duration);
}
