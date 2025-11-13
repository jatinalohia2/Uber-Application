package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.dtos.SignUpDto;
import com.pisoft.uberApp.UberApplication.dtos.UserDto;
import com.pisoft.uberApp.UberApplication.entities.User;

public interface AuthService {

    UserDto sigUp(SignUpDto signUpDto);

    UserDto login(String email , String password);

    void logOut(Long userId); // spring security :

}
