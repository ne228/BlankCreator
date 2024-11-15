package com.smallaxe.blank_creator.blank.service;

import com.smallaxe.blank_creator.blank.entity.Blank;
import com.smallaxe.blank_creator.blank.entity.BlankHub;
import com.smallaxe.blank_creator.blank.entity.BlankHubStatus;
import com.smallaxe.blank_creator.blank.models.BlankHubCreateDto;
import com.smallaxe.blank_creator.blank.models.BlankHubEditDto;
import com.smallaxe.blank_creator.blank.repository.BlankHubRepository;
import com.smallaxe.blank_creator.entity.User;
import com.smallaxe.blank_creator.service.auth.AuthService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BlankHubServiceImpl implements BlankHubService {

    @Autowired
    private BlankHubRepository blankHubRepository;
    @Autowired
    private AuthService authService;

    @Override
    public List<BlankHub> getHubs() throws CredentialException {
        var currentUser = authService.getCurrentUser();

        return blankHubRepository.findBlankHubByUser_Id(currentUser.getId(), Sort.by(Sort.Direction.DESC, "dateCreate"));
    }

    @Override
    public BlankHub getHubById(String id) throws CredentialException {
        var hub = blankHubRepository.findBlankHubById(id);
        if (hub == null) throw new ObjectNotFoundException(id, "Хранилище не найдено");

        var currentUser = authService.getCurrentUser();
        if (!isCanAccessToBlankHub(hub, currentUser))
            hub.setBlankList(hub.getBlankList().stream().filter(blank -> blank.getUser().getId().equals(currentUser.getId())).collect(Collectors.toList()));

        hub.setBlankList(hub.getBlankList()
                .stream()
                .sorted(Comparator.comparing(Blank::getDateCreate).reversed())
                .collect(Collectors.toList()));
        return hub;
    }
    @Override
    public BlankHub create(BlankHubCreateDto blankHubCreateDto) throws CredentialException {
        var blankHub = blankHubCreateDto.toEntity();
        blankHub.setDateCreate(LocalDateTime.now());
        blankHub.setUser(authService.getCurrentUser());
        blankHub.setStatus(BlankHubStatus.ЧЕРНОВИК);
        blankHubRepository.save(blankHub);

        return blankHub;
    }

    @Override
    public BlankHub edit(BlankHubEditDto blankHubEditDto) throws CredentialException {
        var blankHub = getHubById(blankHubEditDto.getId());
        if (blankHub == null)
            throw new ObjectNotFoundException(blankHubEditDto.getId(), "Не найден hub с id " + blankHubEditDto.getId());

        var currentUser = authService.getCurrentUser();
        if (!isCanAccessToBlankHub(blankHub, currentUser))
            throw new AccessDeniedException("Пользователь не имеет доступ к хранилищу");


        blankHub.setNumPr(blankHubEditDto.getNumPr());
        blankHub.setDatePr(blankHubEditDto.getDatePr());
        blankHub.setDateEnrollment(blankHubEditDto.getDateEnrollment());
        blankHubRepository.save(blankHub);

        return blankHub;
    }



    @Override
    public boolean isCanAccessToBlankHub(BlankHub blankHub, User user) {
        return Objects.equals(user.getId(), blankHub.getUser().getId());
    }
}
