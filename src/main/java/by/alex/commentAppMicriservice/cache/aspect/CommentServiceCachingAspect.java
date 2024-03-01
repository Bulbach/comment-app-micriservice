package by.alex.commentAppMicriservice.cache.aspect;

import by.alex.commentAppMicriservice.cache.AbstractCache;
import by.alex.commentAppMicriservice.cache.annotation.CustomCachableGet;
import by.alex.commentAppMicriservice.cache.annotation.CustomCachebleCreate;
import by.alex.commentAppMicriservice.cache.annotation.CustomCachebleDelete;
import by.alex.commentAppMicriservice.cache.annotation.CustomCachebleUpdate;
import by.alex.commentAppMicriservice.dto.ResponseCommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * Аспект для кэширования операций над комментариями.
 * Используется для кэширования результатов методов, помеченных аннотациями кэширования.
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CommentServiceCachingAspect {

    private final AbstractCache<Long, ResponseCommentDto> commentCache;

    /**
     * Точка среза для методов, помеченных аннотацией {@link CustomCachableGet}.
     */
    @Pointcut("@annotation(by.alex.commentAppMicriservice.cache.annotation.CustomCachableGet)")
    public void getId() {
    }

    /**
     * Обрабатывает методы, помеченные аннотацией {@link CustomCachableGet}.
     * Если комментарий с заданным идентификатором есть в кэше, возвращает его из кэша.
     * В противном случае выполняет метод и кэширует результат.
     *
     * @param joinPoint Точка соединения, представляющая метод, помеченный аннотацией {@link CustomCachableGet}.
     * @return Результат выполнения метода.
     * @throws Throwable Если возникает исключение при выполнении метода.
     */
    @Around(value = "getId()")
    public Object cacheHouse(ProceedingJoinPoint joinPoint) throws Throwable {

        Long id = (Long) joinPoint.getArgs()[0];

        if (commentCache.containsKey(id)) {
            return commentCache.get(id);
        } else {
            ResponseCommentDto comment = (ResponseCommentDto) joinPoint.proceed();
            log.info("Founded cache Comment in repository");
            commentCache.put(id, comment);
            return comment;
        }
    }

    /**
     * Точка среза для методов, помеченных аннотацией {@link CustomCachebleCreate}.
     */
    @Pointcut("@annotation(by.alex.commentAppMicriservice.cache.annotation.CustomCachebleCreate) ")
    public void create() {
    }

    /**
     * Обрабатывает методы, помеченные аннотацией {@link CustomCachebleCreate}.
     * Выполняет метод и кэширует результат.
     *
     * @param joinPoint Точка соединения, представляющая метод, помеченный аннотацией {@link CustomCachebleCreate}.
     * @return Результат выполнения метода.
     * @throws Throwable Если возникает исключение при выполнении метода.
     */
    @Around(value = "create()")
    public Object cacheCreate(ProceedingJoinPoint joinPoint) throws Throwable {

        ResponseCommentDto createComment = (ResponseCommentDto) joinPoint.proceed();
        log.info("Created cache Comment " + createComment);
        commentCache.put(createComment.id(), createComment);

        return createComment;
    }

    /**
     * Точка среза для методов, помеченных аннотацией {@link CustomCachebleUpdate}.
     */
    @Pointcut("@annotation(by.alex.commentAppMicriservice.cache.annotation.CustomCachebleUpdate)")
    public void update() {
    }

    /**
     * Обрабатывает методы, помеченные аннотацией {@link CustomCachebleUpdate}.
     * Выполняет метод и кэширует результат.
     *
     * @param joinPoint Точка соединения, представляющая метод, помеченный аннотацией {@link CustomCachebleUpdate}.
     * @return Результат выполнения метода.
     * @throws Throwable Если возникает исключение при выполнении метода.
     */
    @Around(value = "update()")
    public Object cacheUpdate(ProceedingJoinPoint joinPoint) throws Throwable {

        ResponseCommentDto updateComment = (ResponseCommentDto) joinPoint.proceed();
        log.info("Updated cache Comment " + updateComment);
        commentCache.put(updateComment.id(), updateComment);
        return updateComment;
    }

    /**
     * Точка среза для методов, помеченных аннотацией {@link CustomCachebleDelete}.
     */
    @Pointcut("@annotation(by.alex.commentAppMicriservice.cache.annotation.CustomCachebleDelete)")
    public void delete() {
    }

    /**
     * Обрабатывает методы, помеченные аннотацией {@link CustomCachebleDelete}.
     * Удаляет комментарий из кэша и выполняет метод.
     *
     * @param joinPoint Точка соединения, представляющая метод, помеченный аннотацией {@link CustomCachebleDelete}.
     * @return Результат выполнения метода.
     * @throws Throwable Если возникает исключение при выполнении метода.
     */
    @Around(value = "delete()")
    public Object cacheDelete(ProceedingJoinPoint joinPoint) throws Throwable {

        Long id = (Long) joinPoint.getArgs()[0];
        log.info("Deleted cache Comment with id = " + id);
        commentCache.delete(id);

        return joinPoint.proceed();
    }
}
