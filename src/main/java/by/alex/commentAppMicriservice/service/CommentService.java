package by.alex.commentAppMicriservice.service;

import java.util.Collection;
import java.util.List;

/**
 * Интерфейс сервиса для работы с комментариями.
 * Определяет основные операции для работы с ними.
 *
 * @param <K> Тип DTO запроса на создание или обновление комментария.
 * @param <T> Тип DTO комментария.
 */
public interface CommentService<K, T> {

    /**
     * Получает список всех комментариев с пагинацией.
     *
     * @param page Номер страницы.
     * @param size Размер страницы.
     * @return Коллекция DTO комментариев.
     */
    Collection<T> findAll(int page, int size);

    /**
     * Получает комментарий по идентификатору.
     *
     * @param id Идентификатор комментария.
     * @return DTO комментария.
     */
    T findById(Long id);

    /**
     * Создает новый комментарий.
     *
     * @param item DTO запроса на создание комментария.
     * @return DTO созданного комментария.
     */
    T create(K item);

    /**
     * Обновляет комментарий.
     *
     * @param item DTO запроса на обновление комментария.
     * @return DTO обновленного комментария.
     */
    T update(K item);

    /**
     * Удаляет комментарий по идентификатору.
     *
     * @param id Идентификатор комментария.
     */
    void delete(Long id);

    /**
     * Получает список всех комментариев к новости с пагинацией.
     *
     * @param newsId Идентификатор новости.
     * @param page   Номер страницы.
     * @param size   Размер страницы.
     * @return Список DTO комментариев.
     */
    List<T> getAllCommentsForNews(Long newsId, int page, int size);

    /**
     * Получает список всех комментариев используя расширенный поиск с пагинацией.
     *
     * @param search Фрагмент строки для поиска.
     * @param page   Номер страницы.
     * @param size   Размер страницы.
     * @return Список DTO комментариев.
     */
    List<T> search(String search, int page, int size);

}
