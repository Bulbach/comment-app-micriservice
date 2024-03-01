package by.alex.commentAppMicriservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс, представляющий сущность комментария в базе данных.
 * Используется для сопоставления с таблицей "comments" в базе данных.
 */
@Data
@Entity(name = "comments")
public class Comment {

    /**
     * Идентификатор комментария.
     * Генерируется автоматически при добавлении в базу данных.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Время создания или обновления комментария.
     * Обязательное поле.
     */
    @Column(nullable = false)
    private LocalDateTime time;

    /**
     * Текст комментария.
     * Обязательное поле, максимальная длина 500 символов.
     */
    @Column(nullable = false, length = 500)
    private String text;

    /**
     * Имя пользователя, оставившего комментарий.
     * Обязательное поле.
     */
    @Column(nullable = false)
    private String username;

    /**
     * Идентификатор новости, к которой относится комментарий.
     * Обязательное поле.
     */
    @Column(nullable = false)
    private Long newsId;
}
