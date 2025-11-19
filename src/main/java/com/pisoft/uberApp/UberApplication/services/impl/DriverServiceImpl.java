package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.dtos.DriverDto;
import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RiderDto;
import com.pisoft.uberApp.UberApplication.entities.Driver;
import com.pisoft.uberApp.UberApplication.entities.Payment;
import com.pisoft.uberApp.UberApplication.entities.Ride;
import com.pisoft.uberApp.UberApplication.entities.RideRequest;
import com.pisoft.uberApp.UberApplication.enums.PaymentStatus;
import com.pisoft.uberApp.UberApplication.enums.RideRequestStatus;
import com.pisoft.uberApp.UberApplication.enums.RideStatus;
import com.pisoft.uberApp.UberApplication.exception.ResourceNotFound;
import com.pisoft.uberApp.UberApplication.repositories.DriverRepository;
import com.pisoft.uberApp.UberApplication.repositories.RideRepository;
import com.pisoft.uberApp.UberApplication.services.DriverService;
import com.pisoft.uberApp.UberApplication.services.PaymentService;
import com.pisoft.uberApp.UberApplication.services.RideRequestService;
import com.pisoft.uberApp.UberApplication.services.RideService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {

    private static final int PAGE_NUMBER = 0;
    private final RideRequestService rideRequestService;
    private final DriverRepository driverRepository;
    private final RideService rideService;
    private final ModelMapper modelMapper;
    private final PaymentService paymentService;

    @Value("${PAGE_SIZE}")
     Integer PAGE_SIZE;

    @Override
    public RideDto acceptRide(Long rideRequestId) {

         final Integer PAGE_NUMBER = 0;

        RideRequest rideRequest = rideRequestService.findByRideRequestId(rideRequestId);

        Driver currentDriver = getCurrentDriver();


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

        Driver currentDriver = getCurrentDriver();

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

        ride.setStartedAt(LocalDateTime.now());
        Ride updateRide = rideService.updateRideStatus(ride, RideStatus.ONGOING);

        // driver availability = false :
        updateDriverAvailability(currentDriver , false);

        // creating object of payment :
        paymentService.createNewPayment(updateRide);

        return modelMapper.map(updateRide , RideDto.class);
    }

    @Override
    @Transactional
    public RideDto endRide(Long rideId) {

        Driver currentDriver = getCurrentDriver();

        Ride ride = rideService.getById(rideId);

        if (!ride.getDriver().equals(currentDriver)){
            throw new RuntimeException("This Ride doesn't belongs to this Driver");
        }

        if (!ride.getRideStatus().equals(RideStatus.ONGOING)){
            throw new RuntimeException("You can't end Ride with these Ride Status ....");
        }

        ride.setEndedAt(LocalDateTime.now());
        ride.setRideStatus(RideStatus.ENDED);
        Ride savedRide = rideService.saveRide(ride);

        // driver availability = true :
        updateDriverAvailability(currentDriver , true);

        Payment payment = paymentService.findByRide(savedRide);
        paymentService.updatePaymentStatus(payment , PaymentStatus.CONFIRMED);

        // process payment :
        paymentService.processPayment(savedRide , payment);

        // TODO Rating implementation :

        return modelMapper.map(savedRide, RideDto.class);
    }

    @Override
    public RiderDto rateRider(Long riderId, Double rating) {
        return null;
    }

    @Override
    public DriverDto getMyProfile() {
        return modelMapper.map(getCurrentDriver(), DriverDto.class);
    }

    @Override
    public Driver getCurrentDriver() {
        return driverRepository.findById(6L).orElseThrow(()->
                new ResourceNotFound("Driver not found with id" +1L));
    }

    @Override
    public void updateDriverAvailability(Driver driver , boolean isAvailable) {
        driverRepository.save(driver);
    }

    @Override
    public Page<RideDto> getAllMyRides(int pageNo) {

         Pageable pageable = PageRequest.of(pageNo , PAGE_SIZE);


        Driver currentDriver = getCurrentDriver();
        return rideService.getAllRidesOfDriver(currentDriver, pageable).map((
                element) -> modelMapper.map(element, RideDto.class));
    }

}
