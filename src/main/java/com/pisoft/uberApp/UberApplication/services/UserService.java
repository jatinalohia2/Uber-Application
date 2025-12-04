package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.entities.Users;

public interface UserService {

    Users getCurrentLoggedUser();

    Users findById(Long userId);

    void save(Users users);

    Users findByEmail(String email);
}
