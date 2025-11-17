package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.dtos.DriverDto;
import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RiderDto;
import com.pisoft.uberApp.UberApplication.entities.Driver;
import com.pisoft.uberApp.UberApplication.entities.Ride;
import com.pisoft.uberApp.UberApplication.entities.RideRequest;
import com.pisoft.uberApp.UberApplication.enums.RideRequestStatus;
import com.pisoft.uberApp.UberApplication.enums.RideStatus;
import com.pisoft.uberApp.UberApplication.exception.ResourceNotFound;
import com.pisoft.uberApp.UberApplication.repositories.DriverRepository;
import com.pisoft.uberApp.UberApplication.repositories.RideRepository;
import com.pisoft.uberApp.UberApplication.services.DriverService;
import com.pisoft.uberApp.UberApplication.services.RideRequestService;
import com.pisoft.uberApp.UberApplication.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private static final int PAGE_NUMBER = 0;
    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final RideRepository rideRepository;

    @Override
    public RideDto acceptRide(Long rideRequestId) {

         final Integer PAGE_NUMBER = 0;

        RideRequest rideRequest = rideRequestService.findByRideRequestId(rideRequestId);

        Driver currentDriver = findByDriverId();


        if (!rideRequest.getRideRequestStatus().equals(RideRequestStatus.PENDING)){
            throw new RuntimeException("Ride Request cannot be accpeted because it is not im pemding state");
        }

        if (!currentDriver.getAvailable()){
            throw new RuntimeException("Driver is not available for this Ride");
        }

        Ride newRide = rideService.createNewRide(rideRequest, currentDriver);
        return modelMapper.map(newRide , RideDto.class);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public RideDto startRide(Long rideId, String otp) {

        Driver currentDriver = findByDriverId();

        Ride ride = rideService.getById(rideId);

        if (!ride.getDriver().equals(currentDriver)){
            throw new RuntimeException("This Ride does'nt belongs to this Driver");
        }

        if (!ride.getOtp().equals(otp)){
            throw new RuntimeException("Otp did'not match");
        }

        if (!ride.getRideStatus().equals(RideStatus.CONFIRMED)){
            throw new RuntimeException("You can't start Ride with these Ride Status ....");
        }

        Ride updateRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);

        // driver availability = false :
        updateDriverAvailability(currentDriver);

        return modelMapper.map(updateRide , RideDto.class);
    }

    @Override
    public RideDto endRide(Long rideId) {
        return null;
    }

    @Override
    public RiderDto rateRider(Long riderId, Double rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        return null;
    }

    @Override
    public Driver findByDriverId() {
        return driverRepository.findById(1L).orElseThrow(()->
                new ResourceNotFound("Driver not found with id" +1L));
    }

    @Override
    public void updateDriverAvailability(Driver driver) {
        driver.setAvailable(false);
        driverRepository.save(driver);
    }
}
