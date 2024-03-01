package by.alex.commentAppMicriservice.mapper;

import by.alex.commentAppMicriservice.dto.RequestCommentDto;
import by.alex.commentAppMicriservice.dto.ResponseCommentDto;
import by.alex.commentAppMicriservice.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

/**
 * Интерфейс, представляющий маппер для преобразования между сущностью {@link Comment} и DTO {@link RequestCommentDto}/{@link ResponseCommentDto}.
 * Используется для преобразования между объектами приложения и объектами базы данных.
 */
@Component
@Mapper(componentModel = "spring")
public interface CommentMapper {

    /**
     * Преобразует DTO запроса на создание или обновление комментария в сущность комментария.
     *
     * @param requestDto DTO запроса на создание или обновление комментария.
     * @return Сущность комментария.
     */
    Comment toModel(RequestCommentDto requestDto);

    /**
     * Преобразует сущность комментария в DTO ответа с информацией о комментарии.
     *
     * @param comment Сущность комментария.
     * @return DTO ответа с информацией о комментарии.
     */
    ResponseCommentDto toDto(Comment comment);

    /**
     * Обновляет существующую сущность комментария на основе DTO запроса на обновление.
     *
     * @param requestDto DTO запроса на обновление комментария.
     * @param comment    Существующая сущность комментария, которая будет обновлена.
     */
    void updateModel(RequestCommentDto requestDto, @MappingTarget Comment comment);
}
