package by.alex.commentAppMicriservice.service;

import java.util.Collection;
import java.util.List;

public interface CommentService <K,T>{

    Collection<T> findAll(int page, int size);

    T findById(Long id);

    T create(K item);

    T update(K item);

    void delete(Long id);

    List<T> getAllCommentsForNews(Long newsId, int page, int size);
}
