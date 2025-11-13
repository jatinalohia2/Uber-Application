package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.dtos.DriverDto;
import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RiderDto;
import com.pisoft.uberApp.UberApplication.entities.Ride;

import java.util.List;

public interface DriverService {

    RideDto acceptRide(Long rideId);
    public List<RideDto> getAllMyRides();
    RideDto cancelRide(Long rideId );
    RideDto startRide(Long rideId);
    RideDto endRide(Long rideId);
    RiderDto rateRider(Long riderId , Double rating);
    DriverDto getMyProfile(); //


}
