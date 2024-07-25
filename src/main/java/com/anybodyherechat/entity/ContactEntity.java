package com.anybodyherechat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * {@link ContactEntity}
 * Purpose: Relation entity class to represent database table
 */
@Entity(name = "TB_CONTACTS")
@Data
@IdClass(ContactPK.class)
@AllArgsConstructor
@NoArgsConstructor
public class ContactEntity {

    @Id
    private Long userId;
    @Id
    private Long contactId;
    private LocalDateTime createdAt;
}
