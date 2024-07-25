package com.anybodyherechat.service;

import com.anybodyherechat.entity.ContactEntity;
import com.anybodyherechat.exception.ContactException;
import com.anybodyherechat.model.ContactRequestDTO;
import com.anybodyherechat.model.ContactResponseDTO;
import com.anybodyherechat.model.GenericResponse;
import com.anybodyherechat.repository.ContactRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactServiceTest {

    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    @DisplayName("Should create a new contact in database when everything is OK")
    void testSaveContactSuccess() {
        ContactRequestDTO contactRequestDTO = new ContactRequestDTO();
        contactRequestDTO.setUserId(1L);
        contactRequestDTO.setContactId(2L);

        GenericResponse response = contactService.saveContact(contactRequestDTO);

        verify(contactRepository, times(1)).save(any(ContactEntity.class));
        assertInstanceOf(ContactResponseDTO.class, response.getData());
        assertNotNull(response.getData());
    }

    @Test
    @DisplayName("Should throw exception when contact id dont exist")
    void testSaveContactFailure() {
        ContactRequestDTO contactRequestDTO = new ContactRequestDTO();
        contactRequestDTO.setUserId(1L);
        contactRequestDTO.setContactId(99L);

        var errorMessage = "could not execute statement [ERROR: insert or update on table " +
                "\"tb_contacts\" violates foreign key constraint \"tb_contacts_user_id_fkey\"\n  " +
                "Detalhe: Key (user_id)=(1) is not present in table \"tb_users\".] [insert into tb_contacts (created_at,contact_id,user_id) " +
                "values (?,?,?)]; SQL [insert into tb_contacts (created_at,contact_id,user_id) values (?,?,?)];" +
                " constraint [tb_contacts_user_id_fkey]";

        doThrow(new RuntimeException(errorMessage)).when(contactRepository)
                                                   .save(any());

        ContactException exception = Assertions.assertThrows(ContactException.class, () -> {
            contactService.saveContact(contactRequestDTO);
        });

        assertEquals("Error trying to save contact. Error: " + errorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should return a list of contacts by user")
    void testGetContactsSuccess() {
        Long userId = 1L;

        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setUserId(userId);
        contactEntity.setContactId(2L);
        contactEntity.setCreatedAt(LocalDateTime.now());

        when(contactRepository.findAllByUserId(userId)).thenReturn(List.of(contactEntity));

        GenericResponse response = contactService.getContacts(userId);

        verify(contactRepository, times(1)).findAllByUserId(userId);
        assertEquals(1, ((List<?>) response.getData()).size());
    }

    @Test
    @DisplayName("Should throw database error when search by user id")
    void testGetContactsFailure() {
        Long userId = 1L;

        when(contactRepository.findAllByUserId(userId)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(ContactException.class, () -> {
            contactService.getContacts(userId);
        });
        assertEquals("Error trying to get the user contacts list. Error: Database error", exception.getMessage());
    }
}
