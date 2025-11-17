package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.entities.RideRequest;

public interface RideRequestService {

    RideRequest findByRideRequestId(Long rideRequestId);

}
