package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.dtos.*;

public interface AuthService {

    UserDto signUp(SignUpDto signUpDto);

    LoginResponseDto login(LoginDto loginDto);

    void logOut(Long userId); // spring security :

    DriverDto onBoardNewDriver(DriverOnboardDto driverOnboardDto);

    LoginResponseDto generateAccessToken(String refreshToken);
}
