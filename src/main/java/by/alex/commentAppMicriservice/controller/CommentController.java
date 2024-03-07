package by.alex.commentAppMicriservice.controller;

import by.alex.commentAppMicriservice.dto.RequestCommentDto;
import by.alex.commentAppMicriservice.dto.ResponseCommentDto;
import by.alex.commentAppMicriservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для обработки запросов, связанных с комментариями.
 * Обрабатывает HTTP-запросы и вызывает соответствующие методы сервиса комментариев.
 */
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService<ResponseCommentDto, RequestCommentDto> commentService;

    /**
     * Обрабатывает GET-запрос для получения комментария по идентификатору.
     *
     * @param id Идентификатор комментария.
     * @return Ответ с кодом статуса 200 и DTO комментария.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCommentDto> getCommentById(@PathVariable Long id) {
        ResponseCommentDto comment = commentService.findById(id);
        return ResponseEntity.ok(comment);
    }

    /**
     * Обрабатывает POST-запрос для создания нового комментария.
     *
     * @param comment DTO запроса на создание комментария.
     * @return Ответ с кодом статуса 201 и DTO созданного комментария.
     */
    @PostMapping
    public ResponseEntity<ResponseCommentDto> createComment(@RequestBody RequestCommentDto comment) {
        ResponseCommentDto createdComment = commentService.create(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    /**
     * Обрабатывает PUT-запрос для обновления комментария.
     *
     * @param comment DTO запроса на обновление комментария.
     * @return Ответ с кодом статуса 200 и DTO обновленного комментария.
     */
    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> updateComment(@RequestBody RequestCommentDto comment) {
        ResponseCommentDto updatedComment = commentService.update(comment);
        return ResponseEntity.ok(updatedComment);
    }

    /**
     * Обрабатывает DELETE-запрос для удаления комментария по идентификатору.
     *
     * @param commentId Идентификатор комментария.
     * @return Ответ с кодом статуса 204 без тела ответа.
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Обрабатывает GET-запрос для получения всех комментариев с пагинацией.
     *
     * @param page Номер страницы.
     * @param size Размер страницы.
     * @return Ответ с кодом статуса 200 и список DTO комментариев.
     */
    @GetMapping
    public ResponseEntity<List<ResponseCommentDto>> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ResponseCommentDto> comments = (List<ResponseCommentDto>) commentService.findAll(page, size);
        return ResponseEntity.ok(comments);
    }

    /**
     * Обрабатывает GET-запрос для получения всех комментариев к новости с пагинацией.
     *
     * @param newsId Идентификатор новости.
     * @param page   Номер страницы.
     * @param size   Размер страницы.
     * @return Ответ с кодом статуса 200 и список DTO комментариев.
     */
    @GetMapping("/news/{newsId}")
    public ResponseEntity<List<ResponseCommentDto>> getAllCommentsForNews(@PathVariable Long newsId,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {
        List<ResponseCommentDto> comments = commentService.getAllCommentsForNews(newsId, page, size);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/news/{newsId}/comments/{commentId}")
    public ResponseEntity<ResponseCommentDto> getCommentByNewsIdAndCommentId(@PathVariable("newsId") Long newsId,
                                                                             @PathVariable("commentId") Long commentId)
    {
        ResponseCommentDto commentByIdNewsAndComment = commentService.getCommentByNewsIdAndCommentId(newsId, commentId);
        return ResponseEntity.ok(commentByIdNewsAndComment);
    }

    /**
     * Обрабатывает GET-запрос для получения всех комментариев используя расширенный поиск с пагинацией.
     *
     * @param search Фрагмент строки для поиска.
     * @param page   Номер страницы.
     * @param size   Размер страницы.
     * @return Ответ с кодом статуса 200 и список DTO комментариев.
     */
    @GetMapping("/search")
    public ResponseEntity<List<ResponseCommentDto>> fullSearch(@PathVariable String search,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {

        List<ResponseCommentDto> comments = commentService.search(search, page, size);
        return ResponseEntity.ok(comments);
    }
}