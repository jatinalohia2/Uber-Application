package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RideRequestDto;
import com.pisoft.uberApp.UberApplication.entities.Driver;
import com.pisoft.uberApp.UberApplication.entities.Ride;
import com.pisoft.uberApp.UberApplication.entities.RideRequest;
import com.pisoft.uberApp.UberApplication.entities.Rider;
import com.pisoft.uberApp.UberApplication.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RideService {

    Ride getById(Long rideId);
    Ride createNewRide(RideRequest rideRequest , Driver driver);
    Ride updateRideStatus(Ride ride , RideStatus rideStatus);

    Page<Ride> getAllRidesOfRider(Rider rider , Pageable pageable);
    Page<Ride> getAllRidesOfDriver(Driver driver , Pageable pageable);

    Ride saveRide(Ride ride);
}
