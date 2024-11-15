package com.smallaxe.blank_creator.service.auth;

import com.smallaxe.blank_creator.entity.User;
import com.smallaxe.blank_creator.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserPrincipal() throws CredentialException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return (User) authentication.getPrincipal();
        }
        throw new CredentialException("Not authorized");

    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            var usr = (User) authentication.getPrincipal();
            return userRepository.findByUsername(usr.getUsername()).orElse(null);
        }
        return null;
    }
}
