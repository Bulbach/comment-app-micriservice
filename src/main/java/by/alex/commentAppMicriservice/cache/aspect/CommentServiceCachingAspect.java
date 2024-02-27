package by.alex.commentAppMicriservice.cache.aspect;

import by.alex.commentAppMicriservice.cache.AbstractCache;
import by.alex.commentAppMicriservice.dto.ResponseCommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class CommentServiceCachingAspect {

    private final AbstractCache<Long, ResponseCommentDto> commentCache;

    @Pointcut("@annotation(by.alex.commentAppMicriservice.cache.annotation.CustomCachableGet)")
    public void getId() {
    }

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

    @Pointcut("@annotation(by.alex.commentAppMicriservice.cache.annotation.CustomCachebleCreate) ")
    public void create() {
    }

    @Around(value = "create()")
    public Object cacheCreate(ProceedingJoinPoint joinPoint) throws Throwable {

        ResponseCommentDto createComment = (ResponseCommentDto) joinPoint.proceed();
        log.info("Created cache Comment " + createComment);
        commentCache.put(createComment.id(), createComment);

        return createComment;
    }

    @Pointcut("@annotation(by.alex.commentAppMicriservice.cache.annotation.CustomCachebleUpdate)")
    public void update() {
    }

    @Around(value = "update()")
    public Object cacheUpdate(ProceedingJoinPoint joinPoint) throws Throwable {

        ResponseCommentDto updateComment = (ResponseCommentDto) joinPoint.proceed();
        log.info("Updated cache Comment " + updateComment);
        commentCache.put(updateComment.id(), updateComment);
        return updateComment;
    }

    @Pointcut("@annotation(by.alex.commentAppMicriservice.cache.annotation.CustomCachebleDelete)")
    public void delete() {
    }

    @Around(value = "delete()")
    public Object cacheDelete(ProceedingJoinPoint joinPoint) throws Throwable {

        Long id = (Long) joinPoint.getArgs()[0];
        log.info("Deleted cache Comment with id = " + id);
        commentCache.delete(id);

        return joinPoint.proceed();
    }
}
