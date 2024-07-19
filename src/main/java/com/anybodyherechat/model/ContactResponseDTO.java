package com.anybodyherechat.model;

import com.anybodyherechat.entity.ContactEntity;

import java.time.LocalDateTime;

/**
 * {@link ContactResponseDTO}
 * Purpose: Data transfer object class to parse response from entity
 */
public record ContactResponseDTO(Long userId, Long contactId, LocalDateTime createdAt) {

    public static ContactResponseDTO fromContact(ContactEntity contactEntity) {
        return new ContactResponseDTO(contactEntity.getUserId(), contactEntity.getContactId(), contactEntity.getCreatedAt());
    }
}
