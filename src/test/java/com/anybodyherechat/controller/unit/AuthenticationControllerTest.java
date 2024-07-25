package com.anybodyherechat.controller.unit;

import com.anybodyherechat.controller.AuthenticationController;
import com.anybodyherechat.exception.NewUserException;
import com.anybodyherechat.model.UserRequestBody;
import com.anybodyherechat.service.AuthenticationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService mockAuthenticationService;

    @BeforeEach
    public void init() {
        authenticationController = new AuthenticationController(mockAuthenticationService);
    }

    @Test
    public void create_user_with_sucess() {
        Mockito.doNothing().when(mockAuthenticationService).saveNewUser(Mockito.any(UserRequestBody.class));
        ResponseEntity<Void> user = authenticationController.createUser(new UserRequestBody());
        Assertions.assertEquals(user.getStatusCode(), HttpStatus.CREATED,
                () -> "Expected the user was created with sucess");
        Mockito.verify(mockAuthenticationService, Mockito.times(1)).saveNewUser(Mockito.any(UserRequestBody.class));
    }

    @Test
    public void error_when_create_username_already_exists() {
        Mockito.doThrow(new NewUserException("Username already exists")).when(mockAuthenticationService)
               .saveNewUser(Mockito.any(UserRequestBody.class));
        NewUserException newUserException = Assertions.assertThrows(NewUserException.class,
                () -> authenticationController.createUser(new UserRequestBody()));

        Assertions.assertEquals("Username already exists", newUserException.getMessage());
        Mockito.verify(mockAuthenticationService, Mockito.times(1)).saveNewUser(Mockito.any(UserRequestBody.class));
    }

}
