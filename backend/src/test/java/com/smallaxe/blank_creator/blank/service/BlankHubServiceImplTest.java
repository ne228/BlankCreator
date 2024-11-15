package com.smallaxe.blank_creator.blank.service;

import com.smallaxe.blank_creator.blank.entity.BlankHub;
import com.smallaxe.blank_creator.blank.repository.BlankHubRepository;
import com.smallaxe.blank_creator.entity.User;
import com.smallaxe.blank_creator.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import javax.security.auth.login.CredentialException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BlankHubServiceImplTest {
    @Mock
    private BlankHubRepository blankHubRepository;

    @Mock
    private AuthService authService;

    @InjectMocks
    private BlankHubServiceImpl blankHubService;  // Тестируемый объект

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId("user1");
        MockitoAnnotations.openMocks(this);  // Инициализация моков
    }

    @Test
    void test_getHubs() throws CredentialException {

        var hub1 = new BlankHub();
        hub1.setId("1");
        hub1.setUser(user);
        var hub2 = new BlankHub();
        hub2.setId("2");
        hub2.setUser(user);

        var hubs = Arrays.asList(hub1, hub2);
        when(authService.getCurrentUser()).thenReturn(user);
        when(blankHubRepository.findBlankHubByUser_Id(user.getId(), Sort.by(Sort.Direction.ASC, "dateCreate"))).thenReturn(hubs);

        var result = blankHubService.getHubs();
        assertEquals(hubs.size(), result.size());
    }
}