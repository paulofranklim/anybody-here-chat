package com.anybodyherechat.service.impl;

import com.anybodyherechat.entity.UserEntity;
import com.anybodyherechat.exception.NewUserException;
import com.anybodyherechat.model.UserRequestBody;
import com.anybodyherechat.repository.AuthenticationRepository;
import com.anybodyherechat.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationRepository authenticationRepository;

    private final PasswordEncoder encrypt;
    public AuthenticationServiceImpl(AuthenticationRepository authenticationRepository, PasswordEncoder encrypt) {
        this.authenticationRepository = authenticationRepository;
        this.encrypt = encrypt;
    }

    @Override
    public void saveNewUser(UserRequestBody newUser) {
        UserEntity model = UserEntity.toModel(newUser, encrypt);
         boolean userExists = authenticationRepository.existsByUsername(model.getUsername());
        if(userExists){
            throw new NewUserException("Username already exists", HttpStatus.BAD_REQUEST);
        }
        authenticationRepository.save(model);
    }
}
