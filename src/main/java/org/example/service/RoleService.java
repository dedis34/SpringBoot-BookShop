package org.example.service;

import org.example.model.Role;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findById(Long id);
    Optional<Role> findByName(Role.RoleName name);
    Role save(Role role);
}
