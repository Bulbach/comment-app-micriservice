package by.alex.commentAppMicriservice.mapper;

import by.alex.commentAppMicriservice.dto.RequestCommentDto;
import by.alex.commentAppMicriservice.dto.ResponseCommentDto;
import by.alex.commentAppMicriservice.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface CommentMapper {

    Comment toModel(RequestCommentDto requestDto);

    ResponseCommentDto toDto(Comment comment);

    void updateModel(RequestCommentDto requestDto, @MappingTarget Comment comment);
}
