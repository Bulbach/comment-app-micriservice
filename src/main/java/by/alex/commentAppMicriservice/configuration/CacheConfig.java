package by.alex.commentAppMicriservice.configuration;

import by.alex.commentAppMicriservice.cache.AbstractCache;
import by.alex.commentAppMicriservice.cache.impl.LFUCache;
import by.alex.commentAppMicriservice.cache.impl.LRUCache;
import by.alex.commentAppMicriservice.dto.ResponseCommentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.cache")
public class CacheConfig {

    private String algorithm;

    private int max_size;


    @Bean
    public AbstractCache<Long, ResponseCommentDto> commentCache() {
        return "LFU".equals(algorithm)
                ? new LFUCache<>(max_size)
                : new LRUCache<>(max_size);
    }

 }
