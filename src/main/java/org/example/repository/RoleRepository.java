package org.example.repository;

import org.example.model.Role;
import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findByName(Role name);
    List<Role> findAll();
}
