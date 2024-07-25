package com.anybodyherechat.controller.it;

import com.anybodyherechat.config.SecurityConfig;
import com.anybodyherechat.controller.AuthenticationController;
import com.anybodyherechat.model.UserRequestBody;
import com.anybodyherechat.service.AuthenticationService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Tag("IntegrationTest")
@WebMvcTest(AuthenticationController.class)
@Import(SecurityConfig.class)
public class AuthenticationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService mockAuthenticationService;

    @Test
    public void create_user_with_sucess() throws Exception {
        Mockito.doNothing().when(mockAuthenticationService).saveNewUser(Mockito.any(UserRequestBody.class));
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


    }

    @Test
    public void request_to_create_user_is_not_valid() throws Exception {
        Mockito.doNothing().when(mockAuthenticationService).saveNewUser(Mockito.any(UserRequestBody.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/auth/sign-up")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .accept(MediaType.APPLICATION_JSON)).
               andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}
