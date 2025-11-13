package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.dtos.DriverDto;
import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RideRequestDto;
import com.pisoft.uberApp.UberApplication.entities.Ride;
import com.pisoft.uberApp.UberApplication.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RideService {

    Ride getById(Long rideId);
    void matchWithDriver(RideRequestDto rideRequestDto);
    Ride createNewRide(RideRequestDto rideRequestDto , DriverDto driverDto);
    Ride updateRideStatus(Long id , RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Long riderId , PageRequest pageRequest);
    Page<Ride> getAllRidesOfDriver(Long driverId , PageRequest pageRequest);



}
