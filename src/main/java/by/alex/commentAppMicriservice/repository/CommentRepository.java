package by.alex.commentAppMicriservice.repository;

import by.alex.commentAppMicriservice.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByNewsId(Long newsId, Pageable pageable);
}
