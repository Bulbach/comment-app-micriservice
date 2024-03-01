package by.alex.commentAppMicriservice.repository;

import by.alex.commentAppMicriservice.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Интерфейс, представляющий репозиторий для работы с сущностями {@link Comment} в базе данных.
 * Расширяет {@link JpaRepository}, предоставляя стандартные методы CRUD и дополнительные методы
 * для поиска комментариев по идентификатору новости.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Находит все комментарии, связанные с определенной новостью, с использованием пагинации.
     *
     * @param newsId   Идентификатор новости, к которой относятся комментарии.
     * @param pageable Объект, определяющий параметры пагинации.
     * @return Список комментариев, связанных с новостью.
     */
    List<Comment> findByNewsId(Long newsId, Pageable pageable);
}
