package com.smallaxe.blank_creator.blank.service;

import com.smallaxe.blank_creator.blank.entity.Blank;
import com.smallaxe.blank_creator.blank.entity.BlankStatus;
import com.smallaxe.blank_creator.blank.entity.BlankTemplate;
import com.smallaxe.blank_creator.blank.models.ImportTemplateDto;
import com.smallaxe.blank_creator.blank.repository.BlankRepository;
import com.smallaxe.blank_creator.blank.repository.BlankTemplateRepository;
import com.smallaxe.blank_creator.service.auth.AuthService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlankTemplateServiceImpl implements BLankTemplateService {

    @Autowired
    private BlankTemplateRepository blankTemplateRepository;

    @Autowired
    BlankRepository blankRepository;
    @Autowired
    BlankHubService blankHubService;
    @Autowired
    private AuthService authService;

    @Override
    public List<BlankTemplate> getBlankTemplates() throws CredentialException {
        var currentUser = authService.getCurrentUser();
        return blankTemplateRepository.findBlankTemplateByUser_Id(currentUser.getId(), Sort.by(Sort.Direction.DESC, "dateCreate"));
    }


    @Override
    public BlankTemplate getBlankTemplateById(String blankTemplateId) throws CredentialException {
        var currentUser = authService.getCurrentUser();
        return blankTemplateRepository.findBlankTemplateByUser_IdAndId(currentUser.getId(), blankTemplateId, Sort.by(Sort.Direction.DESC, "dateCreate"));
    }

    @Override
    public Blank addBlankTemplateToHub(ImportTemplateDto dto) throws CredentialException {
        var blankTemplate = getBlankTemplateById(dto.getTemplateId());

        if (blankTemplate == null)
            throw new ObjectNotFoundException(dto.getTemplateId(), "Не найден шаблон");

        var blankHub = blankHubService.getHubById(dto.getHubId());
        if (blankHub == null)
            throw new ObjectNotFoundException(dto.getHubId(), "Не найдено хранилище");

        var blank = blankTemplate.toBlank();

        blank.setDateCreate(LocalDateTime.now());
        blank.setBlankStatus(BlankStatus.ЧЕРНОВИК);
        blank.setUser(authService.getCurrentUser());
        blank.setHubId(blankHub.getId());
        blankRepository.save(blank);
        return blank;
    }

    @Override
    public Integer deleteBlankTemplate(String id) {
        if (!blankTemplateRepository.existsById(id))
            throw new ObjectNotFoundException(id, "Не найден шаблон");

        blankTemplateRepository.deleteById(id);
        return 0;
    }
}
