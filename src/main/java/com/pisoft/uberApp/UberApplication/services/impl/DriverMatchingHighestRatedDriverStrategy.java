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
public class DriverMatchingHighestRatedDriverStrategy implements DriverMatchingStrategy {

    private final DriverRepository driverRepository;

    @Override
    public List<Driver> matchDrivers(RideRequest rideRequest) {
        return driverRepository.findTopRatedDrivers(rideRequest.getPickUpLocation());
    }
}

