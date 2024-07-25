package com.anybodyherechat.controller.it;

import com.anybodyherechat.config.SecurityConfig;
import com.anybodyherechat.entity.UserEntity;
import com.anybodyherechat.model.UserRequestBody;
import com.anybodyherechat.repository.AuthenticationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Tag("IntegrationTest")
@Testcontainers
@Import(SecurityConfig.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIT {

    private static final String TEST_DATABASE_NAME = "authentication_test_db";
    private static PostgreSQLContainer postgreSQLContainer;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    @Autowired
    private PasswordEncoder encrypt;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        postgreSQLContainer = new PostgreSQLContainer("postgres:latest").withDatabaseName(TEST_DATABASE_NAME);
        postgreSQLContainer.start();

        registry.add("spring.datasource.url", () -> postgreSQLContainer.getJdbcUrl());
        registry.add("spring.datasource.username", () -> postgreSQLContainer.getUsername());
        registry.add("spring.datasource.password", () -> postgreSQLContainer.getPassword());
    }

    @Test
    public void create_user_with_sucess() throws Exception {

        UserRequestBody requestBody = new UserRequestBody();
        requestBody.setEmail("xxxx@xxx.com");
        requestBody.setPassword("123");
        requestBody.setUsername("teste");
        String s = new ObjectMapper().writeValueAsString(requestBody);
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(s)
                                              .accept(MediaType.APPLICATION_JSON)).
               andExpect(MockMvcResultMatchers.status().isCreated());
        boolean isExists = authenticationRepository.existsByUsername(requestBody.getUsername());
        Assertions.assertTrue(isExists,
                () -> "If the request is valid, the username and email is unique, the data should be exists on " +
                        "database");

    }

    @Test
    public void request_to_create_user_is_not_valid() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .accept(MediaType.APPLICATION_JSON)).
               andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    public void error_when_try_to_insert_a_username_already_exists() throws Exception {
        UserRequestBody teste1 = new UserRequestBody();
        teste1.setEmail("xxxxx1@xxx.com");
        teste1.setPassword("123");
        teste1.setUsername("teste1");
        UserEntity model = UserEntity.toModel(teste1, encrypt);
        authenticationRepository.save(model);
        String s = new ObjectMapper().writeValueAsString(teste1);
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(s)
                                              .accept(MediaType.APPLICATION_JSON)).
               andExpect(MockMvcResultMatchers.status().isBadRequest());

        int result = authenticationRepository.countByUsername(teste1.getUsername());
        int expected = 1;
        Assertions.assertEquals(expected, result, () -> "Expected one value with the username " + teste1.getUsername());
    }

    @Test
    public void error_when_try_to_insert_a_email_already_exists() throws Exception {
        UserRequestBody teste = new UserRequestBody();
        teste.setEmail("already@exists.com");
        teste.setPassword("123");
        teste.setUsername("alreadyExists");
        UserEntity model = UserEntity.toModel(teste, encrypt);
        authenticationRepository.save(model);
        String s = new ObjectMapper().writeValueAsString(teste);
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(s)
                                              .accept(MediaType.APPLICATION_JSON)).
               andExpect(MockMvcResultMatchers.status().isBadRequest());

        int result = authenticationRepository.countByEmail(teste.getEmail());
        int expected = 1;
        Assertions.assertEquals(expected, result, () -> "Expected one value with the email" + teste.getEmail());
    }


}
