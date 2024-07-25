package com.anybodyherechat.controller;

import com.anybodychat.api.AuthorizationApi;
import com.anybodyherechat.model.UserRequestBody;
import com.anybodyherechat.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController implements AuthorizationApi {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }


    @Override
    public ResponseEntity<Void> createUser(UserRequestBody userRequestBody) {
        authenticationService.saveNewUser(userRequestBody);
        ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.CREATED);
        return response;
    }

    @Override
    public ResponseEntity<Void> loginUser() {
        return null;
    }

    @Override
    public ResponseEntity<Void> logoutUser() {
        return null;
    }
}
