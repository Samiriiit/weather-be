package com.sapient.sde.weather_predictor.service;

import com.sapient.sde.weather_predictor.dto.WeatherResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class WeatherCacheService implements WeatherCache {
    private static final String CACHE_PREFIX = "weather::";

    @Autowired
    private RedisTemplate<String, WeatherResponseDto> redisTemplate;

    @Override
    public WeatherResponseDto get(String city) {
        return redisTemplate.opsForValue().get(CACHE_PREFIX + city.toLowerCase());
    }

    @Override
    public void save(String city, WeatherResponseDto dto) {
        redisTemplate.opsForValue().set(CACHE_PREFIX + city.toLowerCase(), dto);
    }
}
