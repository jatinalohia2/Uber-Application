package com.pisoft.uberApp.UberApplication.contollers;

import com.pisoft.uberApp.UberApplication.dtos.SignUpDto;
import com.pisoft.uberApp.UberApplication.dtos.UserDto;
import com.pisoft.uberApp.UberApplication.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signUp")
    public UserDto signUpDto(@RequestBody SignUpDto signUpDto){
        return authService.signUp(signUpDto);
    }
}
