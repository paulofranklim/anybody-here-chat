package com.anybodyherechat.service;

import com.anybodyherechat.model.UserRequestBody;

public interface AuthenticationService {

    public void saveNewUser(UserRequestBody newUser);
}
