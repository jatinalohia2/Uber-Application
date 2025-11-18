package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.dtos.DriverDto;
import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RiderDto;
import com.pisoft.uberApp.UberApplication.entities.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DriverService {

    RideDto acceptRide(Long requestedRideId);
    RideDto cancelRide(Long rideId );
    RideDto startRide(Long rideId, String otp);
    RideDto endRide(Long rideId);
    RiderDto rateRider(Long riderId , Double rating);
    DriverDto getMyProfile(); //
    Driver getCurrentDriver();
    void updateDriverAvailability(Driver driver);

    Page<RideDto> getAllMyRides(int pageNo);
}
