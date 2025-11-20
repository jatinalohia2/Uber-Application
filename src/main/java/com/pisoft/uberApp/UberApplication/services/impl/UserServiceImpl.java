package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.entities.Users;
import com.pisoft.uberApp.UberApplication.exception.ResourceNotFound;
import com.pisoft.uberApp.UberApplication.repositories.UserRepository;
import com.pisoft.uberApp.UberApplication.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Users getCurrentLoggedUser() {
        return userRepository.findById(1L).orElseThrow(()->
                new ResourceNotFound("User not found with Id : "+1L));
    }
}
