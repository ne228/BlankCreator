package com.smallaxe.blank_creator.blank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smallaxe.blank_creator.blank.entity.Blank;
import com.smallaxe.blank_creator.blank.models.BlankPrintDto;
import com.smallaxe.blank_creator.service.auth.AuthService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PrintServiceImpl implements PrintService {
    @Value("${api_py}")
    private String url;
    @Autowired
    private AuthService authService;
    @Autowired
    private BlankService blankService;

    public byte[] print(List<Blank> blanks) throws Exception {
        var currentUser = authService.getCurrentUser();

        if (blanks.stream().anyMatch(blank -> !blankService.isCanAccessToBlank(blank, currentUser)))
            throw new AccessDeniedException("Нет доступа к одному из бланков");

        var blankModels = blanks.stream().map(blank -> new BlankPrintDto().toModel(blank)).collect(Collectors.toList());

        // Замените на ваш URL
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost post = new HttpPost(url);

            // Подготовка JSON
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String json = objectMapper.writeValueAsString(blankModels);

            // Установка заголовков
            post.setHeader("Content-Type", "application/json");
            post.setEntity(new StringEntity(json, StandardCharsets.UTF_8));

            // Выполнение POST-запроса
            try (CloseableHttpResponse response = client.execute(post)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toByteArray(entity);
                } else {
                    throw new IOException("Пустой ответ от сервера");
                }
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            throw new IOException("Ошибка при создании DOCX: " + exc.getMessage(), exc);
        }
    }
}
