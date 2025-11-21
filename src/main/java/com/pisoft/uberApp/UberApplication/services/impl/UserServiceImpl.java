package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.entities.Users;
import com.pisoft.uberApp.UberApplication.exception.ResourceNotFound;
import com.pisoft.uberApp.UberApplication.repositories.UserRepository;
import com.pisoft.uberApp.UberApplication.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Users getCurrentLoggedUser() {
        return findById(1L);
    }

    @Override
    public Users findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->
                new ResourceNotFound("User not found with Id : "+1L));
    }

    @Override
    public void save(Users users) {
        userRepository.save(users);
    }
}
