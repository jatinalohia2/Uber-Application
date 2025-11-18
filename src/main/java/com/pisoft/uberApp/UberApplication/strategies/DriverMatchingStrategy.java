package com.pisoft.uberApp.UberApplication.strategies;

import com.pisoft.uberApp.UberApplication.entities.Driver;
import com.pisoft.uberApp.UberApplication.entities.RideRequest;

import java.util.List;

public interface DriverMatchingStrategy {
    List<Driver> matchDrivers(RideRequest rideRequest);
}

