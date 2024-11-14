package com.smallaxe.blank_creator;


import com.smallaxe.blank_creator.entity.Role;
import com.smallaxe.blank_creator.entity.User;
import com.smallaxe.blank_creator.repositories.RoleRepository;
import com.smallaxe.blank_creator.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;
    @Autowired
    private RoleRepository roleRepository;

    private final String email = "user@mail.ru";
    private final String username = "user228";
    private final String rawPassword = "qwerty123";

    private Role roleUser;


    @BeforeEach
    void setUp() {
        roleUser = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));
    }

    @Test
    void testCreateUser() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(rawPassword);

        User newUser = new User(username, email, password);
        newUser.addRole(roleUser);
        repo.save(newUser);

        User savedUser = repo.findByUsername(username).orElse(null);

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo(username);
        assertThat(savedUser.getRoles().stream().filter(role -> role.getName().equals(roleUser.getName()))).contains(roleUser);
    }

}
