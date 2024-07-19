package com.anybodyherechat.service;

import com.anybodyherechat.entity.ContactEntity;
import com.anybodyherechat.exception.ContactException;
import com.anybodyherechat.model.ContactRequestDTO;
import com.anybodyherechat.model.ContactResponseDTO;
import com.anybodyherechat.model.GenericResponse;
import com.anybodyherechat.repository.ContactRepository;
import com.anybodyherechat.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * {@link ContactService}
 * Purpose: Service class to provide logic to the methods implementation
 */
@Service
@Slf4j
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public GenericResponse saveContact(ContactRequestDTO contact) {
        log.info("Entry method saveContact(ContactRequestDTO contact)");
        try {
            var entity = new ContactEntity();
            entity.setUserId(contact.getUserId());
            entity.setContactId(contact.getContactId());
            entity.setCreatedAt(LocalDateTime.now());
            log.debug("entity: {}", entity);
            contactRepository.save(entity);

            return ResponseUtils.buildGenericResponse(Strings.EMPTY, ContactResponseDTO.fromContact(entity));
        } catch (RuntimeException e) {
            var message = "Error trying to save contact. Error: " + e.getMessage();
            log.error(message);
            throw new ContactException(message);
        }
    }

    public GenericResponse getContacts(Long userId) {
        log.info("Entry method getContacts(Long userId)");
        try {
            var result = contactRepository.findAllByUserId(userId).stream()
                                          .map(ContactResponseDTO::fromContact).toList();
            log.debug("result: {}", result);
            return ResponseUtils.buildGenericResponse(Strings.EMPTY, result);
        } catch (RuntimeException e) {
            var message = "Error trying to get the user contacts list. Error: " + e.getMessage();
            log.error(message);
            throw new ContactException(message);
        }
    }
}
