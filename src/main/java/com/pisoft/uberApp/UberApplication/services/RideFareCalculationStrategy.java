package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.dtos.RideRequestDto;

public interface RideFareCalculationStrategy {

    Double calculateFare(RideRequestDto rideRequestDto);


}
