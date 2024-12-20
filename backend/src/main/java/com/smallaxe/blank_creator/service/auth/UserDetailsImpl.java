package com.smallaxe.blank_creator.service.auth;

import com.smallaxe.blank_creator.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 * The above class while returning the built UserDetails Object uses
 * the below referred code
 */
public class UserDetailsImpl implements UserDetails {


    /**
     * this class uses UserDetails Interface which is available in spring
     * security core library
     */
    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String email;

    @JsonIgnore
    private String password;

    public UserDetailsImpl(String id, String username,
                           String email, String password) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;

    }

    public static UserDetailsImpl build(User user) {

        return new UserDetailsImpl(user.getId(),
                user.getUsername(), user.getEmail(),
                user.getPassword());
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public Collection<? extends GrantedAuthority>
    getAuthorities() {

        return null;
    }
}
