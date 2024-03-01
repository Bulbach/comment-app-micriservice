package by.alex.commentAppMicriservice.configuration;

import by.alex.commentAppMicriservice.cache.AbstractCache;
import by.alex.commentAppMicriservice.cache.impl.LFUCache;
import by.alex.commentAppMicriservice.cache.impl.LRUCache;
import by.alex.commentAppMicriservice.dto.ResponseCommentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Класс конфигурации для настройки кэша комментариев.
 * Используется для создания и настройки экземпляра кэша комментария.
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "spring.cache")
public class CacheConfig {

    /**
     * Алгоритм кэширования, который будет использоваться для кэширования комментариев.
     * Поддерживаемые алгоритмы: LFU (Least Frequently Used) и LRU (Least Recently Used).
     */
    private String algorithm;

    /**
     * Максимальный размер кэша комментариев.
     */
    private int max_size;

    /**
     * Создает и настраивает экземпляр кэша комментариев в зависимости от выбранного алгоритма.
     *
     * @return Экземпляр кэша комментариев.
     */
    @Bean
    public AbstractCache<Long, ResponseCommentDto> commentCache() {
        return "LFU".equals(algorithm)
                ? new LFUCache<>(max_size)
                : new LRUCache<>(max_size);
    }

 }
