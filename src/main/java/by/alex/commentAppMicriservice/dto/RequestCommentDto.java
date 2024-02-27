package by.alex.commentAppMicriservice.dto;

import java.time.LocalDateTime;

public record RequestCommentDto(

        Long id,
        LocalDateTime time,
        String text,
        String username,
        String newsId
)
{}
