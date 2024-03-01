package by.alex.commentAppMicriservice.dto;

import java.time.LocalDateTime;
/**
 * Класс, представляющий DTO ответа с информацией о комментарии.
 * Используется для передачи данных о комментарии между различными частями приложения.
 *
 * @param id       Идентификатор комментария.
 * @param time     Время создания или обновления комментария.
 * @param text     Текст комментария.
 * @param username Имя пользователя, оставившего комментарий.
 * @param newsId   Идентификатор новости, к которой относится комментарий.
 */
public record ResponseCommentDto(

        Long id,
        LocalDateTime time,
        String text,
        String username,
        String newsId
)
{}
