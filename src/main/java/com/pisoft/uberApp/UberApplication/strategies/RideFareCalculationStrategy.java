package com.pisoft.uberApp.UberApplication.strategies;

import com.pisoft.uberApp.UberApplication.entities.RideRequest;

public interface RideFareCalculationStrategy {

    Double calculateFare(RideRequest rideRequest);


}
