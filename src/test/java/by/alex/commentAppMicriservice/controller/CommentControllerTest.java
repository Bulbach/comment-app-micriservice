package by.alex.commentAppMicriservice.controller;

import by.alex.commentAppMicriservice.dto.RequestCommentDto;
import by.alex.commentAppMicriservice.dto.ResponseCommentDto;
import by.alex.commentAppMicriservice.service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CommentService<ResponseCommentDto, RequestCommentDto> commentService;

    @Test
    public void getCommentById_success() throws Exception {
        Long commentId = 1L;
        ResponseCommentDto comment = new ResponseCommentDto(commentId, LocalDateTime.MAX, "Test Comment", "testUser", "1");
        Mockito.when(commentService.findById(commentId)).thenReturn(comment);

        mockMvc.perform(get("/comments/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentId))
                .andExpect(jsonPath("$.text").value(comment.text()))
                .andExpect(jsonPath("$.username").value(comment.username()))
                .andExpect(jsonPath("$.newsId").value(comment.newsId()));
    }

    @Test
    public void createComment_success() throws Exception {
        RequestCommentDto requestComment = new RequestCommentDto(null, LocalDateTime.MAX, "This is a new comment", "newUser", "2");
        ResponseCommentDto createdComment = new ResponseCommentDto(2L, requestComment.time(), requestComment.text(), requestComment.username(), requestComment.newsId());
        Mockito.when(commentService.create(requestComment)).thenReturn(createdComment);

        mockMvc.perform(post("/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestComment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(createdComment.id()))
                .andExpect(jsonPath("$.text").value(createdComment.text()))
                .andExpect(jsonPath("$.username").value(createdComment.username()))
                .andExpect(jsonPath("$.newsId").value(createdComment.newsId()));
    }

    @Test
    public void updateComment_success() throws Exception {
        Long commentId = 1L;
        RequestCommentDto requestComment = new RequestCommentDto(null, LocalDateTime.MAX, "This is an updated comment", "updatedUser", "3");
        ResponseCommentDto updatedComment = new ResponseCommentDto(commentId, requestComment.time(), requestComment.text(), requestComment.username(), requestComment.newsId());
        Mockito.when(commentService.update(requestComment)).thenReturn(updatedComment);

        mockMvc.perform(put("/comments/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestComment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedComment.id()))
                .andExpect(jsonPath("$.text").value(updatedComment.text()))
                .andExpect(jsonPath("$.username").value(updatedComment.username()))
                .andExpect(jsonPath("$.newsId").value(updatedComment.newsId()));
    }

    @Test
    public void deleteComment_success() throws Exception {
        Long commentId = 1L;
        Mockito.doNothing().when(commentService).delete(commentId);

        mockMvc.perform(delete("/comments/{id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getAllComments_success() throws Exception {
        List<ResponseCommentDto> commentList = List.of(
                new ResponseCommentDto(1L, LocalDateTime.MAX, "This is comment 1", "user1", "4"),
                new ResponseCommentDto(2L, LocalDateTime.MAX, "This is comment 2", "user2", "5")
        );
        Mockito.when(commentService.findAll(0, 10)).thenReturn(commentList);

        mockMvc.perform(get("/comments")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
