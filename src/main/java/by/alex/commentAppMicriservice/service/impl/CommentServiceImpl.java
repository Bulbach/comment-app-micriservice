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
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.engine.search.sort.dsl.SearchSortFactory;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, представляющий реализацию интерфейса {@link CommentService} для работы с комментариями.
 * Используется для выполнения операций над сущностями {@link Comment} и преобразования между DTO.
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService<RequestCommentDto, ResponseCommentDto> {

    private final CommentRepository repository;
    @Qualifier("commentMapperImpl")
    private final CommentMapper mapper;

    private final EntityManagerFactory entityManagerFactory;

    @Value("${search.field.username}")
    private String FIELD_USERNAME;

    @Value("${search.field.text}")
    private String FIELD_TEXT;

    @Value("${search.boost.username}")
    private float USERNAME_BOOST_FACTOR;

    @Value("${search.boost.text}")
    private float TEXT_BOOST_FACTOR;

    /**
     * Получает список всех комментариев с пагинацией.
     *
     * @param page Номер страницы.
     * @param size Размер страницы.
     * @return Список DTO ответов с информацией о комментариях.
     */
    @Override
    public List<ResponseCommentDto> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Получает комментарий по идентификатору.
     *
     * @param id Идентификатор комментария.
     * @return DTO ответа с информацией о комментарии.
     * @throws RuntimeException Если комментарий с указанным идентификатором не найден.
     */
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

    /**
     * Создает новый комментарий.
     *
     * @param item DTO запроса на создание комментария.
     * @return DTO ответа с информацией о созданном комментарии.
     * @throws RuntimeException Если переданный объект равен null.
     */
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

    /**
     * Обновляет существующий комментарий.
     *
     * @param item DTO запроса на обновление комментария.
     * @return DTO ответа с информацией об обновленном комментарии.
     * @throws RuntimeException Если переданный объект равен null или комментарий с указанным идентификатором не найден.
     */
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

    /**
     * Удаляет комментарий по идентификатору.
     *
     * @param id Идентификатор комментария.
     * @throws RuntimeException Если комментарий с указанным идентификатором не найден.
     */
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

    /**
     * Получает список всех комментариев для определенной новости с пагинацией.
     *
     * @param newsId Идентификатор новости.
     * @param page   Номер страницы.
     * @param size   Размер страницы.
     * @return Список DTO ответов с информацией о комментариях.
     */
    @Override
    public List<ResponseCommentDto> getAllCommentsForNews(Long newsId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return repository.findByNewsId(newsId, pageable)
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Метод для получения всех комментариев используя расширенный поиск с пагинацией.
     *
     * @param search Фрагмент строки для поиска.
     * @param page   Номер страницы.
     * @param size   Размер страницы.
     * @return Возвращает коллекцию ResponseCommentDto.
     */
    @Override
    public List<ResponseCommentDto> search(String search, int page, int size) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        SearchSession searchSession = Search.session(entityManager);

        SearchResult<Comment> searchResult = searchSession.search(Comment.class)
                .where(comment -> comment
                        .bool()
                        .with(b -> {
                            b.must(comment.matchAll());
                            b.must(comment.match()
                                    .field(FIELD_USERNAME)
                                    .boost(USERNAME_BOOST_FACTOR)
                                    .field(FIELD_TEXT)
                                    .boost(TEXT_BOOST_FACTOR)
                                    .matching(search));
                        }))
                .sort(SearchSortFactory::score)
                .fetch(page, size);

        return searchResult.hits().stream().map(mapper::toDto).collect(Collectors.toList());
    }
}
