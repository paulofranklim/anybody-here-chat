package com.anybodyherechat.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * {@link ContactPK}
 * Purpose: Class to represent primary key combination for {@link ContactEntity}
 */
@AllArgsConstructor
@NoArgsConstructor
public class ContactPK implements Serializable {
    protected Long userId;
    protected Long contactId;
}
