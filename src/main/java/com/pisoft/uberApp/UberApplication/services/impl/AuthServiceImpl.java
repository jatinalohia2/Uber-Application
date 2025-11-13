package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.dtos.SignUpDto;
import com.pisoft.uberApp.UberApplication.dtos.UserDto;
import com.pisoft.uberApp.UberApplication.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public UserDto sigUp(SignUpDto signUpDto) {
        return null;
    }

    @Override
    public UserDto login(String email, String password) {
        return null;
    }

    @Override
    public void logOut(Long userId) {

    }
}
