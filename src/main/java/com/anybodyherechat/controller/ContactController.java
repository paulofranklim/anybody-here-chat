package com.anybodyherechat.controller;

import com.anybodychat.api.ContactApi;
import com.anybodyherechat.model.ContactRequestDTO;
import com.anybodyherechat.model.GenericResponse;
import com.anybodyherechat.service.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@link ContactController}
 * Purpose: Rest controller class with contact endpoints operations
 */
@RestController
@Slf4j
public class ContactController implements ContactApi {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public ResponseEntity<GenericResponse> addContact(ContactRequestDTO contactRequestDTO) {
        log.info("Entry method addContact(ContactRequestDTO contactRequestDTO)");
        log.debug("Adding contact {}", contactRequestDTO);
        return ResponseEntity.ok(contactService.saveContact(contactRequestDTO));
    }

    @Override
    public ResponseEntity<GenericResponse> getUserContacts(Long userId) {
        log.info("Entry method getUserContacts(Long userId)");
        return ResponseEntity.ok(contactService.getContacts(userId));
    }
}
