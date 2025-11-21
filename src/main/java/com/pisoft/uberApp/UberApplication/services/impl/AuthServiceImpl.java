package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.dtos.DriverDto;
import com.pisoft.uberApp.UberApplication.dtos.DriverOnboardDto;
import com.pisoft.uberApp.UberApplication.dtos.SignUpDto;
import com.pisoft.uberApp.UberApplication.dtos.UserDto;
import com.pisoft.uberApp.UberApplication.entities.Driver;
import com.pisoft.uberApp.UberApplication.entities.Users;
import com.pisoft.uberApp.UberApplication.enums.Roles;
import com.pisoft.uberApp.UberApplication.exception.ResourceNotFound;
import com.pisoft.uberApp.UberApplication.repositories.UserRepository;
import com.pisoft.uberApp.UberApplication.services.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

import static com.pisoft.uberApp.UberApplication.enums.Roles.DRIVER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RiderService riderService ;
    private final WalletService walletService;
    private final UserService userService;
    private final DriverService driverService;


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

    @Override
    public DriverDto onBoardNewDriver(Long userId, DriverOnboardDto driverOnboardDto) {

        Users users = userService.findById(userId);

        if (users.getRoles().contains(DRIVER)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST ,
                    "User is already exist as a DRIVER role");
        }

        Driver driver = Driver
                .builder()
                .available(true)
                .averageRating(0.0)
                .licNo(driverOnboardDto.getLicNo())
                .totalRating(0)
                .users(users)
                .build();

        Driver driver1 = driverService.onBoardNewDriver(driver);

        users.getRoles().add(DRIVER);
        userService.save(users);
        return modelMapper.map(driver1, DriverDto.class);
    }
}
