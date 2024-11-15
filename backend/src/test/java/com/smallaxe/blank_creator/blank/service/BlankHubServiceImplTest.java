package com.smallaxe.blank_creator.blank.service;

import com.smallaxe.blank_creator.blank.entity.Blank;
import com.smallaxe.blank_creator.blank.entity.BlankHub;
import com.smallaxe.blank_creator.blank.repository.BlankHubRepository;
import com.smallaxe.blank_creator.entity.User;
import com.smallaxe.blank_creator.service.auth.AuthService;
import lombok.SneakyThrows;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import javax.security.auth.login.CredentialException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class BlankHubServiceImplTest {
    @Mock
    private BlankHubRepository blankHubRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private BlankHubServiceImpl blankHubService;  // Тестируемый объект

    private User currentUser;
    private User anotherUser;
    private final String blankHub1Id = "1";
    private final String blankHub2Id = "1";

    private Blank userCreatorBlank;
    private Blank userNotCreatorBlank;
    @BeforeEach
    public void setUp() {
        currentUser = new User();
        currentUser.setId("user1");

        anotherUser = new User();
        anotherUser.setId("user2");

        userCreatorBlank = new Blank();
        userCreatorBlank.setUser(currentUser);
        userCreatorBlank.setId("1");

        userNotCreatorBlank = new Blank();
        userNotCreatorBlank.setUser(anotherUser);
        userNotCreatorBlank.setId("1");
        MockitoAnnotations.openMocks(this);  // Инициализация моков
    }

    @Test
    void test_getHubs() throws CredentialException {

        var hub1 = new BlankHub();
        hub1.setId(blankHub1Id);
        hub1.setUser(currentUser);
        var hub2 = new BlankHub();
        hub2.setId(blankHub2Id);
        hub2.setUser(currentUser);

        var hubs = Arrays.asList(hub1, hub2);
        when(authService.getCurrentUser()).thenReturn(currentUser);
        when(blankHubRepository.findBlankHubByUser_Id(currentUser.getId(), Sort.by(Sort.Direction.DESC, "dateCreate"))).thenReturn(hubs);

        var result = blankHubService.getHubs();
        assertEquals(hubs.size(), result.size());
    }

    @SneakyThrows
    @Test
    void test_getBlankHubById_throwsObjectNotFound() {
//        var hub1 = new BlankHub();
//        hub1.setId(blankHub1Id);
//        hub1.setUser(currentUser);

        when(authService.getCurrentUser()).thenReturn(currentUser);
        when(blankHubRepository.findById(blankHub1Id)).thenReturn(null);

        assertThrows(ObjectNotFoundException.class, () -> {
            blankHubService.getHubById(blankHub1Id);
        });
    }

    @SneakyThrows
    @Test
    void test_getBlankHubById_throwsObjectNotFound() {
        var hub1 = new BlankHub();
        hub1.setId(blankHub1Id);
        hub1.setUser(currentUser);

        when(authService.getCurrentUser()).thenReturn(currentUser);
        when(blankHubRepository.findById(blankHub1Id)).thenReturn(null);

        assertThrows(ObjectNotFoundException.class, () -> {
            blankHubService.getHubById(blankHub1Id);
        });
    }
}