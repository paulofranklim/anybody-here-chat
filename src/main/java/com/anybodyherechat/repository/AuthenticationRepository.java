package com.anybodyherechat.repository;


import com.anybodyherechat.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthenticationRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByUsername(String username);
}
