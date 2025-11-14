package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.entities.Driver;
import com.pisoft.uberApp.UberApplication.entities.RideRequest;
import com.pisoft.uberApp.UberApplication.repositories.DriverRepository;
import com.pisoft.uberApp.UberApplication.services.DriverMatchingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverMatchingNearestDriverStartegy implements DriverMatchingStrategy {


    private final DriverRepository driverRepository;

    @Override
    public List<Driver> findMatchingDriver(RideRequest rideRequest) {

        // find the nearest Driver :
        // TODO implement email as well :
        return driverRepository.findNearestDriver(rideRequest.getPickUpLocation());
    }
}
