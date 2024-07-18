package com.anybodyherechat.repository;

import com.anybodyherechat.entity.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * {@link ContactRepository}
 * Purpose: Repository class to provide database connected methods
 */
@Repository
public interface ContactRepository extends JpaRepository<ContactEntity, Long> {
    List<ContactEntity> findAllByUserId(Long userId);
}
