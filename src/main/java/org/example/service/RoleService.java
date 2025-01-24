package org.example.service;

import org.example.model.Role;
import java.util.List;
import java.util.Optional;

public interface RoleService {
    Role save(Role role);
    Optional<Role> findByName(Role name);
    List<Role> findAll();
}