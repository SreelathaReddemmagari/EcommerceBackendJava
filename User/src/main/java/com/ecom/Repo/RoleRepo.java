package com.ecom.Repo;

import com.ecom.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role,Integer> {
    Role findByRoleName(String roleName);
}
