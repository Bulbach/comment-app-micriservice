package by.alex.commentAppMicriservice.controller;

import by.alex.commentAppMicriservice.dto.RequestCommentDto;
import by.alex.commentAppMicriservice.dto.ResponseCommentDto;
import by.alex.commentAppMicriservice.service.CommentService;
import by.alex.commentAppMicriservice.service.impl.CommentServiceImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentServiceImpl commentService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseCommentDto> getCommentById(@PathVariable Long id) {
        ResponseCommentDto comment = commentService.findById(id);
        return ResponseEntity.ok(comment);
    }

    @PostMapping
    public ResponseEntity<ResponseCommentDto> createComment(@RequestBody RequestCommentDto comment) {
        ResponseCommentDto createdComment = commentService.create(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseCommentDto> updateComment(@RequestBody RequestCommentDto comment) {
        ResponseCommentDto updatedComment = commentService.update(comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long newsId, @PathVariable Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ResponseCommentDto>> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ResponseCommentDto> comments = commentService.findAll(page, size);
        return ResponseEntity.ok(comments);
    }


    @GetMapping("/news/{newsId}")
    public ResponseEntity<List<ResponseCommentDto>> getAllCommentsForNews(@PathVariable Long newsId,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {
        List<ResponseCommentDto> comments = commentService.getAllCommentsForNews(newsId, page, size);
        return ResponseEntity.ok(comments);
    }

    @Value(value = "${spring.cache.algorithm}")
    String s;

    @PostConstruct
    public void post(){
        System.out.println(s);
    }
}