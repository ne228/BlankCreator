package com.smallaxe.blank_creator.blank.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.smallaxe.blank_creator.blank.entity.Blank;
import com.smallaxe.blank_creator.blank.entity.BlankHub;
import com.smallaxe.blank_creator.blank.entity.BlankHubStatus;
import com.smallaxe.blank_creator.blank.entity.BlankStatus;
import com.smallaxe.blank_creator.blank.models.*;
import com.smallaxe.blank_creator.blank.repository.BlankHubRepository;
import com.smallaxe.blank_creator.blank.repository.BlankRepository;
import com.smallaxe.blank_creator.entity.User;
import com.smallaxe.blank_creator.service.auth.AuthService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BlankService {
    private final AuthService authService;
    private final BlankRepository blankRepository;
    private final BlankHubRepository blankHubRepository;

    @Value("${api_py}")
    private String url;

    public BlankService(AuthService authService, BlankRepository blankRepository, BlankHubRepository blankHubRepository) {
        this.authService = authService;
        this.blankRepository = blankRepository;
        this.blankHubRepository = blankHubRepository;

    }

    public BlankHub blankCreate(BlankHubCreateDto blankHubCreateDto) throws CredentialException {
        var blankHub = blankHubCreateDto.toEntity();
        blankHub.setDateCreate(LocalDateTime.now());
        blankHub.setUser(authService.getCurrentUser());
        blankHub.setStatus(BlankHubStatus.ЧЕРНОВИК);
        blankHubRepository.save(blankHub);

        return blankHub;
    }

    public BlankHub blankHubEdit(BlankHubEditDto blankHubEditDto) throws CredentialException {
        var blankHub = getHubById(blankHubEditDto.getId());
        if (blankHub == null)
            throw new ObjectNotFoundException(blankHubEditDto.getId(), "Не найден hub с id " + blankHubEditDto.getId());

        var currentUser = authService.getCurrentUser();
        if (!isCanAccessToBlankHub(blankHub, currentUser))
            throw new AccessDeniedException("Пользователь не имеет доступ к хранилищу");


        blankHub.setNumPr(blankHubEditDto.getNumPr());
        blankHub.setDatePr(blankHubEditDto.getDatePr());
        blankHubRepository.save(blankHub);

        return blankHub;
    }

    public Blank blankCreate(BlankCreateDto dto) throws CredentialException {
        var blankHub = getHubById(dto.getHubId());
        Blank blank = dto.toEntity();
        blank.setDateCreate(LocalDateTime.now());
        blank.setBlankStatus(BlankStatus.ЧЕРНОВИК);
        blank.setUser(authService.getCurrentUser());
        blank.setHubId(blankHub.getId());
        blankRepository.save(blank);

//        blankHub.getBlankList().add(blank);
//        blankHubRepository.save(blankHub);


        return blank;
    }

    public List<BlankHub> getHubs() throws CredentialException {
        var currentUser = authService.getCurrentUser();
        return blankHubRepository.findBlankHubByUser_Id(currentUser.getId(), Sort.by(Sort.Direction.DESC, "dateCreate"));
    }

    public BlankHub getHubById(String id) throws CredentialException {
        var currentUser = authService.getCurrentUser();
        var hub = blankHubRepository.findById(id).orElse(null);
        if (hub == null) throw new ObjectNotFoundException(id, "Хранилище не найдено");
        if (!isCanAccessToBlankHub(hub, currentUser))
            hub.setBlankList(hub.getBlankList().stream().filter(blank -> blank.getUser().getId().equals(currentUser.getId())).collect(Collectors.toList()));

        hub.setBlankList(hub.getBlankList()
                .stream()
                .sorted(Comparator.comparing(Blank::getDateCreate).reversed())
                .collect(Collectors.toList()));
        return hub;
    }


    public Blank getBlankById(String blankId) throws CredentialException {
        var blank = blankRepository.findBlankById(blankId);
        if (blank == null) throw new ObjectNotFoundException(blankId, "Справка не найдена");

        var currentUser = authService.getCurrentUser();
        if (!isCanAccessToBlank(blank, currentUser))
            throw new AccessDeniedException("Пользователь не имеет доступа к справк");

        return blank;
    }

    public Blank editBlank(BlankEditDto dto) throws CredentialException {
        // Validation
        var blank = getBlankById(dto.getId());
        if (blank == null) throw new ObjectNotFoundException(dto.getId(), "Не найден бланк с id " + dto.getId());

        var currentUser = authService.getCurrentUser();
        if (!isCanAccessToBlank(blank, currentUser))
            throw new AccessDeniedException("Пользователь не имеет доступ к справке");

        // Save
        blank = dto.toEntity(blank);
        blankRepository.save(blank);
        return blank;
    }

    public Blank rejectBlank(String blankId) throws CredentialException {
        // Validation
        var blank = getBlankById(blankId);
        if (blank == null) throw new ObjectNotFoundException(blankId, "Не найден бланк с id " + blankId);

        var currentUser = authService.getCurrentUser();
        if (!isCanAccessToBlankHub(blank.getHub(), currentUser))
            throw new AccessDeniedException("Пользователь не имеет доступ к справке");

        // Save
        blank.setBlankStatus(BlankStatus.ОТКЛОНЕН);
        blankRepository.save(blank);
        blank.setDateSuccess(LocalDateTime.now());
        return blank;
    }

    public Blank successBlank(String blankId) throws CredentialException {
        // Validation
        var blank = getBlankById(blankId);
        if (blank == null) throw new ObjectNotFoundException(blankId, "Не найден бланк с id " + blankId);

        var currentUser = authService.getCurrentUser();
        if (!isCanAccessToBlankHub(blank.getHub(), currentUser))
            throw new AccessDeniedException("Пользователь не имеет доступ к справке");

        // Save
        blank.setBlankStatus(BlankStatus.УСПЕШНО);
        blank.setDateSuccess(LocalDateTime.now());
        blankRepository.save(blank);
        return blank;
    }

    public byte[] print(List<Blank> blanks) throws Exception {
        var currentUser = authService.getCurrentUser();

        if (blanks.stream().anyMatch(blank -> !isCanAccessToBlank(blank, currentUser)))
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

    public boolean isEditable(Blank blank) {
        return blank.getBlankStatus() == BlankStatus.ЧЕРНОВИК;
    }

    public boolean isCanAccessToBlankHub(BlankHub blankHub, User user) {
        return Objects.equals(user.getId(), blankHub.getUser().getId());
    }

    public boolean isCanAccessToBlank(Blank blank, User user) {
        return Objects.equals(user.getId(), blank.getUser().getId()) || isCanAccessToBlankHub(blank.getHub(), user);
    }

}
