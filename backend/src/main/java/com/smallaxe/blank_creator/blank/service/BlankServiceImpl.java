package com.smallaxe.blank_creator.blank.service;

import com.smallaxe.blank_creator.blank.entity.Blank;
import com.smallaxe.blank_creator.blank.entity.BlankStatus;
import com.smallaxe.blank_creator.blank.entity.BlankTemplate;
import com.smallaxe.blank_creator.blank.models.BlankCreateDto;
import com.smallaxe.blank_creator.blank.models.BlankEditDto;
import com.smallaxe.blank_creator.blank.repository.BlankRepository;
import com.smallaxe.blank_creator.blank.repository.BlankTemplateRepository;
import com.smallaxe.blank_creator.entity.User;
import com.smallaxe.blank_creator.service.auth.AuthService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class BlankServiceImpl implements BlankService {
    @Autowired
    private AuthService authService;
    @Autowired
    private BlankRepository blankRepository;
    @Autowired
    private BlankTemplateRepository blankTemplateRepository;
    @Autowired
    private BlankHubService blankHubService;


    @Override
    public Blank create(BlankCreateDto dto) throws CredentialException {
        var blankHub = blankHubService.getHubById(dto.getHubId());
        var currentUser = authService.getCurrentUser();
        Blank blank = dto.toEntity();
        blank.setDateCreate(LocalDateTime.now());
        blank.setBlankStatus(BlankStatus.ЧЕРНОВИК);
        blank.setUser(currentUser);
        blank.setHubId(blankHub.getId());
        blankRepository.save(blank);

        if (dto.isSaveAsTemplate()) {
            var blankTemplate = new BlankTemplate();
            blankTemplate.toTemplate(blank);
            blankTemplate.setDateCreate(LocalDateTime.now());
            blankTemplate.setUser(currentUser);
            blankTemplateRepository.save(blankTemplate);
        }
        return blank;
    }

    @Override
    public Blank getBlankById(String blankId) throws CredentialException {
        var blank = blankRepository.findBlankById(blankId);
        if (blank == null) throw new ObjectNotFoundException(blankId, "Справка не найдена");

        var currentUser = authService.getCurrentUser();
        if (!isCanAccessToBlank(blank, currentUser))
            throw new AccessDeniedException("Пользователь не имеет доступа к справк");

        return blank;
    }

    @Override
    public Blank edit(BlankEditDto dto) throws CredentialException {
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

    @Override
    public Blank rejectBlank(String blankId) throws CredentialException {
        // Validation
        var blank = getBlankById(blankId);
        if (blank == null) throw new ObjectNotFoundException(blankId, "Не найден бланк с id " + blankId);

        var currentUser = authService.getCurrentUser();
        if (!blankHubService.isCanAccessToBlankHub(blank.getHub(), currentUser))
            throw new AccessDeniedException("Пользователь не имеет доступ к справке");

        // Save
        blank.setBlankStatus(BlankStatus.ОТКЛОНЕН);
        blankRepository.save(blank);
        blank.setDateSuccess(LocalDateTime.now());
        return blank;
    }

    @Override
    public Blank successBlank(String blankId) throws CredentialException {
        // Validation
        var blank = getBlankById(blankId);
        if (blank == null) throw new ObjectNotFoundException(blankId, "Не найден бланк с id " + blankId);

        var currentUser = authService.getCurrentUser();
        if (!blankHubService.isCanAccessToBlankHub(blank.getHub(), currentUser))
            throw new AccessDeniedException("Пользователь не имеет доступ к справке");

        // Save
        blank.setBlankStatus(BlankStatus.УСПЕШНО);
        blank.setDateSuccess(LocalDateTime.now());
        blankRepository.save(blank);
        return blank;
    }


    @Override
    public boolean isCanAccessToBlank(Blank blank, User user) {
        return Objects.equals(user.getId(), blank.getUser().getId()) || blankHubService.isCanAccessToBlankHub(blank.getHub(), user);
    }


    @Override
    public List<Blank> getAllBlanks() throws CredentialException {
        return blankRepository.findBlanksByUser_Id(authService.getCurrentUser().getId());
    }
}
