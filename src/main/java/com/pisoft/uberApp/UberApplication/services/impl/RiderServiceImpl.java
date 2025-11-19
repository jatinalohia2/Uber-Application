package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.dtos.DriverDto;
import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RideRequestDto;
import com.pisoft.uberApp.UberApplication.dtos.RiderDto;
import com.pisoft.uberApp.UberApplication.entities.RideRequest;
import com.pisoft.uberApp.UberApplication.entities.Rider;
import com.pisoft.uberApp.UberApplication.entities.Users;
import com.pisoft.uberApp.UberApplication.enums.RideRequestStatus;
import com.pisoft.uberApp.UberApplication.repositories.RideRequestRepository;
import com.pisoft.uberApp.UberApplication.repositories.RiderRepository;
import com.pisoft.uberApp.UberApplication.services.*;
import com.pisoft.uberApp.UberApplication.strategies.RideStrategyManager;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RiderServiceImpl implements RiderService {

    private final ModelMapper modelMapper;
    private final RideRequestRepository rideRequestRepository;
    private final RiderRepository riderRepository;
    private final RideStrategyManager rideStrategyManager;
    private final RideService rideService;

    @Value("${PAGE_SIZE}")
    Integer PAGE_SIZE;

    @Override
    public RideRequestDto requestRide(RideRequestDto rideRequestDto) {

        // TODO change this implementation inside riderService
        Rider currentRider = getCurrentRider();

        RideRequest rideRequest = modelMapper.map(rideRequestDto, RideRequest.class);
        rideRequest.setRideRequestStatus(RideRequestStatus.PENDING);
        rideRequest.setRider(currentRider); // set rider :

        // calculate fare :

        Double fare = rideStrategyManager.rideFareCalculationStrategy().calculateFare(rideRequest);
        rideRequest.setFare(fare);

        RideRequest saveRideRequest = rideRequestRepository.save(rideRequest);

        // matching Drivers :
        // TODO email is remaining....
        rideStrategyManager.findMatchingDriver(currentRider.getRating()).matchDrivers(rideRequest);

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
        Rider currentRider = getCurrentRider();
        return modelMapper.map(currentRider, RiderDto.class);
    }

    @Override
    public Page<RideDto> getAllMyRides(int pageNo) {

        Rider currentRider = getCurrentRider();
        Pageable pageable = PageRequest.of(pageNo , PAGE_SIZE);

        return rideService.getAllRidesOfRider(currentRider, pageable).map((
                element) -> modelMapper.map(element, RideDto.class));


    }



    @Override
    public Rider getCurrentRider() {
        //TODO : we have not implement spring sec. to get current logged user :
        return riderRepository.findById(1L).orElse(null);
    }

    @Override
    public Rider createNewRider(Users users) {
        Rider rider = Rider.builder()
                .users(users)
                .rating(0.0)
                .build();
        return riderRepository.save(rider);
    }

    public RideRequestDto convertRideReqToRideReqDto (RideRequest rideRequest){
        return modelMapper.map(rideRequest, RideRequestDto.class);
    }

    public RideRequest convertRideReqDtoToRideReq(RideRequestDto rideRequestDto){
        return modelMapper.map(rideRequestDto, RideRequest.class);
    }

}
