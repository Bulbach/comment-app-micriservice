package by.alex.commentAppMicriservice.service.impl;

import by.alex.commentAppMicriservice.cache.annotation.CustomCachableGet;
import by.alex.commentAppMicriservice.cache.annotation.CustomCachebleCreate;
import by.alex.commentAppMicriservice.cache.annotation.CustomCachebleDelete;
import by.alex.commentAppMicriservice.cache.annotation.CustomCachebleUpdate;
import by.alex.commentAppMicriservice.dto.RequestCommentDto;
import by.alex.commentAppMicriservice.dto.ResponseCommentDto;
import by.alex.commentAppMicriservice.entity.Comment;
import by.alex.commentAppMicriservice.mapper.CommentMapper;
import by.alex.commentAppMicriservice.repository.CommentRepository;
import by.alex.commentAppMicriservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService<RequestCommentDto, ResponseCommentDto> {

    private final CommentRepository repository;
    @Qualifier("commentMapperImpl")
    private final CommentMapper mapper;

    @Override
    public List<ResponseCommentDto> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @CustomCachableGet
    public ResponseCommentDto findById(Long id) {

        if (id == 0) {
            throw new RuntimeException("Id can`t be null ");
        }
        Comment comment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment with id = " + id + " not found"));

        return mapper.toDto(comment);
    }

    @Override
    @CustomCachebleCreate
    public ResponseCommentDto create(RequestCommentDto item) {

        if (item == null) {
            throw new RuntimeException("Object can`t null");
        }
        Comment comment = mapper.toModel(item);
        Comment saveComment = repository.save(comment);

        return mapper.toDto(saveComment);
    }

    @Override
    @CustomCachebleUpdate
    public ResponseCommentDto update(RequestCommentDto item) {

        if (item == null) {
            throw new RuntimeException("Object can`t null");
        }
        Comment commentById = repository.findById(item.id())
                .orElseThrow(() -> new RuntimeException("Comments with id= " + item.id() + "not found"));
        mapper.updateModel(item, commentById);

        return mapper.toDto(commentById);
    }

    @Override
    @CustomCachebleDelete
    public void delete(Long id) {

        if (id == 0) {
            throw new RuntimeException("Id can`t be null ");
        }
        Comment commentById = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comments with id= " + id + "not found"));
        repository.delete(commentById);
    }


    @Override
    public List<ResponseCommentDto> getAllCommentsForNews(Long newsId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repository.findByNewsId(newsId, pageable)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
