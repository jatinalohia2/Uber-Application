package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.dtos.SignUpDto;
import com.pisoft.uberApp.UberApplication.dtos.UserDto;
import com.pisoft.uberApp.UberApplication.entities.Rider;
import com.pisoft.uberApp.UberApplication.entities.Users;
import com.pisoft.uberApp.UberApplication.entities.Wallet;
import com.pisoft.uberApp.UberApplication.enums.Roles;
import com.pisoft.uberApp.UberApplication.repositories.RiderRepository;
import com.pisoft.uberApp.UberApplication.repositories.UserRepository;
import com.pisoft.uberApp.UberApplication.repositories.WalletRepository;
import com.pisoft.uberApp.UberApplication.services.AuthService;
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
    private final RiderRepository riderRepository;
    private final WalletRepository walletRepository;


    @Override
    public UserDto sigUp(SignUpDto signUpDto) {

        Optional<Users> optional = userRepository.findByEmail(signUpDto.getEmail());

        if (optional.isPresent()){
            throw new RuntimeException("User Already Exist ....");
        }


        // user creation :
        Users users = modelMapper.map(signUpDto, Users.class);
        users.setRoles(Set.of(Roles.RIDER));
        Users saveUser = userRepository.save(users);

        // rider creation :

        Rider rider = Rider.builder()
                .users(saveUser)
                .rating(0.0)
                .build();

        riderRepository.save(rider);

        // Wallet Creation:

        Wallet wallet = Wallet.builder()
                .users(saveUser)
                .balance(0.0)
                .build();

        walletRepository.save(wallet);

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
