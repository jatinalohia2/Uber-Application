package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RideRequestDto;
import com.pisoft.uberApp.UberApplication.entities.Driver;
import com.pisoft.uberApp.UberApplication.entities.Ride;
import com.pisoft.uberApp.UberApplication.entities.RideRequest;
import com.pisoft.uberApp.UberApplication.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RideService {

    Ride getById(Long rideId);
    void matchWithDriver(RideRequestDto rideRequestDto);
    Ride createNewRide(RideRequest rideRequest , Driver driver);
    Ride updateRideStatus(Ride ride , RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Long riderId , PageRequest pageRequest);
    Page<Ride> getAllRidesOfDriver(Long driverId , PageRequest pageRequest);
    List<RideDto> findAllRidesOfDriver();
}
