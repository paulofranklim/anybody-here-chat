package com.anybodyherechat.controller.it;

import com.anybodyherechat.entity.ContactEntity;
import com.anybodyherechat.entity.UserEntity;
import com.anybodyherechat.model.ContactRequestDTO;
import com.anybodyherechat.repository.AuthenticationRepository;
import com.anybodyherechat.repository.ContactRepository;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("IntegrationTest")
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@ImportAutoConfiguration
@Testcontainers
@SpringBootTest
class ContactControllerIT {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        Flyway flyway = Flyway.configure().cleanDisabled(false).dataSource(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        ).load();
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void create_contact_with_success() throws Exception {
        createNewUsers();

        var contactRequestDTO = new ContactRequestDTO();
        contactRequestDTO.setUserId(1L);
        contactRequestDTO.setContactId(2L);

        String request = new ObjectMapper().writeValueAsString(contactRequestDTO);
        mockMvc.perform(post("/contact")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(request))
               .andExpect(status().isOk());
    }

    @Test
    void error_when_create_contact_with_user_not_exists() throws Exception {
        var contactRequestDTO = new ContactRequestDTO();
        contactRequestDTO.setUserId(1L);
        contactRequestDTO.setContactId(2L);

        String request = new ObjectMapper().writeValueAsString(contactRequestDTO);
        mockMvc.perform(post("/contact")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(request))
               .andExpect(status().isBadRequest());
    }

    @Test
    void return_user_contact_list_success() throws Exception {
        createNewUsers();

        contactRepository.save(new ContactEntity(1L, 2L, LocalDateTime.now()));
        contactRepository.save(new ContactEntity(1L, 3L, LocalDateTime.now()));

        mockMvc.perform(get("/contact/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.length()").value(2))
               .andExpect(jsonPath("$.data[0].userId").value(1L));
    }

    @Test
    void return_empty_user_contact_list_success() throws Exception {
        mockMvc.perform(get("/contact/99"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.length()").value(0));
    }

    private void createNewUsers() {
        UserEntity user1 = new UserEntity();
        user1.setUsername("user 1");
        user1.setPassword("user 1");
        user1.setEmail("user 1");
        user1.setCreateDatetime(LocalDateTime.now());
        user1.setId(1L);

        UserEntity user2 = new UserEntity();
        user2.setUsername("user 2");
        user2.setPassword("user 2");
        user2.setEmail("user 2");
        user2.setCreateDatetime(LocalDateTime.now());
        user2.setId(2L);

        UserEntity user3 = new UserEntity();
        user3.setUsername("user 3");
        user3.setPassword("user 3");
        user3.setEmail("user 3");
        user3.setCreateDatetime(LocalDateTime.now());
        user3.setId(3L);

        authenticationRepository.saveAll(List.of(user1, user2, user3));
    }
}

