package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.dtos.DriverDto;
import com.pisoft.uberApp.UberApplication.dtos.DriverOnboardDto;
import com.pisoft.uberApp.UberApplication.dtos.SignUpDto;
import com.pisoft.uberApp.UberApplication.dtos.UserDto;

public interface AuthService {

    UserDto signUp(SignUpDto signUpDto);

    UserDto login(String email , String password);

    void logOut(Long userId); // spring security :

    DriverDto onBoardNewDriver(Long userId, DriverOnboardDto driverOnboardDto);

}
