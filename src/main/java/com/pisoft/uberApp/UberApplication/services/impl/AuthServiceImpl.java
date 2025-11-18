package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.dtos.SignUpDto;
import com.pisoft.uberApp.UberApplication.dtos.UserDto;
import com.pisoft.uberApp.UberApplication.entities.Rider;
import com.pisoft.uberApp.UberApplication.entities.Users;
import com.pisoft.uberApp.UberApplication.entities.Wallet;
import com.pisoft.uberApp.UberApplication.enums.Roles;
import com.pisoft.uberApp.UberApplication.exception.ResourceNotFound;
import com.pisoft.uberApp.UberApplication.repositories.RiderRepository;
import com.pisoft.uberApp.UberApplication.repositories.UserRepository;
import com.pisoft.uberApp.UberApplication.repositories.WalletRepository;
import com.pisoft.uberApp.UberApplication.services.AuthService;
import com.pisoft.uberApp.UberApplication.services.RiderService;
import com.pisoft.uberApp.UberApplication.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService ;
    private final WalletService walletService;


    @Override
    public UserDto signUp(SignUpDto signUpDto) {

        Optional<Users> optional = userRepository.findByEmail(signUpDto.getEmail());

        if (optional.isPresent()){
            throw new ResourceNotFound("User Already Exist ....");
        }

        // user creation :
        Users users = modelMapper.map(signUpDto, Users.class);
        users.setRoles(Set.of(Roles.RIDER));
        Users saveUser = userRepository.save(users);

        // rider creation :
        riderService.createNewRider(saveUser);

        // Wallet Creation:
        walletService.createNewWallet(saveUser);
        return modelMapper.map(saveUser , UserDto.class);
    }

    @Override
    public UserDto login(String email, String password) {
        return null;
    }

    @Override
    public void logOut(Long userId) {

    }
}
