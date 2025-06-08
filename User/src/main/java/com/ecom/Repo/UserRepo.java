package com.ecom.Repo;

import com.ecom.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    User findByEmail(String email);
    User findByUsername(String username);
}
