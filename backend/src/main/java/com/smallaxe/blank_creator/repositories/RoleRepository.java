package com.smallaxe.blank_creator.repositories;

import com.smallaxe.blank_creator.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {

    public Optional<Role> findRoleByName(String name);

    boolean existsByName(String role);


    Optional<Role> findByName(String role_user);
}