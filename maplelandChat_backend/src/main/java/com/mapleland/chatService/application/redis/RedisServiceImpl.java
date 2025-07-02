package com.mapleland.chatService.application.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void set(String key, String value, Duration duration) {
        if (duration != null) {
            redisTemplate.opsForValue().set(key, value, duration);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    @Override
    public long increment(String key) {
        return redisTemplate.opsForValue().increment(key, 1);
    }

    @Override
    public long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key, 1);
    }

    @Override
    public void expire(String key, Duration duration) {
        redisTemplate.expire(key, duration);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean exists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    @Override
    public Long decreaseAndDeleteIfZero(String key) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(
                "local cnt = redis.call('DECR', KEYS[1]) " +
                        "if cnt <= 0 then redis.call('DEL', KEYS[1]) end " +
                        "return math.max(cnt, 0)"
        );
        script.setResultType(Long.class);
        return redisTemplate.execute(script, Collections.singletonList(key));
    }


    @Override
    public long getTTLSec(String key) {
        Long expire = redisTemplate.getExpire(key);

        if (expire == null) {
            return 0;
        } else {
            return Duration.ofSeconds(expire).toSeconds();
        }
    }

    @Override
    public long getTTLMin(String key) {
        Long expire = redisTemplate.getExpire(key);

        if (expire == null) {
            return 0;
        } else {
            return Duration.ofSeconds(expire).toMinutes();
        }
    }
}
