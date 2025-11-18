package com.pisoft.uberApp.UberApplication.strategies.impl;

import com.pisoft.uberApp.UberApplication.entities.RideRequest;
import com.pisoft.uberApp.UberApplication.services.DistanceService;
import com.pisoft.uberApp.UberApplication.strategies.RideFareCalculationStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideFareDefaultCalculationStrategy implements RideFareCalculationStrategy {

    private final DistanceService distanceService;

    @Value("${RIDE_PER_KM_CHARGES}")
    private double RIDE_PER_KM_CHARGES;

    @Override
    public Double calculateFare(RideRequest rideRequest) {
        // fare calculate :
        double distance = distanceService.calculateDistance(rideRequest.getPickUpLocation(), rideRequest.getDropOffLocation());
        return distance * RIDE_PER_KM_CHARGES;
    }
}
