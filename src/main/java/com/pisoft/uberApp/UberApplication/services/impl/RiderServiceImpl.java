package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.dtos.DriverDto;
import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RideRequestDto;
import com.pisoft.uberApp.UberApplication.dtos.RiderDto;
import com.pisoft.uberApp.UberApplication.entities.RideRequest;
import com.pisoft.uberApp.UberApplication.entities.Rider;
import com.pisoft.uberApp.UberApplication.enums.RideRequestStatus;
import com.pisoft.uberApp.UberApplication.repositories.RideRequestRepository;
import com.pisoft.uberApp.UberApplication.repositories.RiderRepository;
import com.pisoft.uberApp.UberApplication.services.DriverMatchingStrategy;
import com.pisoft.uberApp.UberApplication.services.DriverService;
import com.pisoft.uberApp.UberApplication.services.RideFareCalculationStrategy;
import com.pisoft.uberApp.UberApplication.services.RiderService;
import com.pisoft.uberApp.UberApplication.strategies.RideStrategy;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideStrategy rideStrategy;


    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

        // TODO change this implementation inside riderService
        Rider currentRider = getCurrentRider();

        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(currentRider); // set rider :

        // calculate fare :

        Double fare = rideStrategy.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest saveRideRequest = rideRequestRepository.save(rideRequest);

        // matching Drivers :
        // TODO email is remaining....
        rideStrategy.findMatchingDriver(currentRider.getRating()).matchDrivers(rideRequest);

        return convertRideReqToRideReqDto(rideRequest);
    }

    @Override
    public RideDto cancelRide(Long rideId) {
        return null;
    }

    @Override
    public DriverDto rateDriver(Long driverId, Double rating) {
        return null;
    }

    @Override
    public RiderDto getMyProfile() {
        return null;
    }

    @Override
    public List<RideDto> getAllMyRides() {
        return List.of();
    }



    @Override
    public Rider getCurrentRider() {
        //TODO : we have not implement spring sec. to get current logged user :
        return riderRepository.findById(1L).orElse(null);
    }

    public RideRequestDto convertRideReqToRideReqDto (RideRequest rideRequest){
        return modelMapper.map(rideRequest, RideRequestDto.class);
    }

    public RideRequest convertRideReqDtoToRideReq(RideRequestDto rideRequestDto){
        return modelMapper.map(rideRequestDto, RideRequest.class);
    }

}
