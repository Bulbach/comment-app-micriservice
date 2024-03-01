package by.alex.commentAppMicriservice.service.impl;

import by.alex.commentAppMicriservice.dto.NewsDto;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Интерфейс, представляющий клиента для взаимодействия с внешним API новостей.
 * Используется для вызова методов API и получения данных о новостях.
 */
@FeignClient(name = "comments", url = "http://localhost:8081/news")
public interface APIClient {

    /**
     * Получает новость по идентификатору с помощью вызова API.
     *
     * @param id Идентификатор новости.
     * @return DTO новости.
     */
    @GetMapping("/{id}")
    NewsDto getNewsById(@PathVariable("id") Long id);
}
