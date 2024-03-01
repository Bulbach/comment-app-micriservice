package by.alex.commentAppMicriservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
/**
 * Класс, представляющий DTO новости.
 * Используется для получения результата от микросервиса News
 * при использовании feign-client
 */
@Data
public class NewsDto {
    /**
     * Идентификатор новости.
     */
    private Long id;

    /**
     * Время создания новости.
     */
    private LocalDateTime time;

    /**
     * Заголовок новости.
     */
    private String title;

    /**
     * Текст новости.
     */
    private String text;
}
