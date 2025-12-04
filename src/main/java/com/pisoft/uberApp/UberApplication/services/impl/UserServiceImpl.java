package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.entities.Users;
import com.pisoft.uberApp.UberApplication.exception.ResourceNotFound;
import com.pisoft.uberApp.UberApplication.repositories.UserRepository;
import com.pisoft.uberApp.UberApplication.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService  , UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public Users getCurrentLoggedUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()){
            throw new RuntimeException("No current logged User");
        }
        return (Users) authentication.getPrincipal();
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

    @Override
    public Users findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->
                new ResourceNotFound("Email not exist : "+email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByEmail(username);
    }
}
