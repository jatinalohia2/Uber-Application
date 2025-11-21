package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RideRequestDto;
import com.pisoft.uberApp.UberApplication.dtos.RiderDto;
import com.pisoft.uberApp.UberApplication.entities.Rider;
import com.pisoft.uberApp.UberApplication.entities.Users;
import org.springframework.data.domain.Page;

public interface RiderService {

    RideRequestDto requestRide(RideRequestDto rideRequestDto);
    RideDto cancelRide(Long rideId);
    RiderDto getMyProfile();
    Page<RideDto> getAllMyRides(int pageNo); // we can't pass rider id , because that comes from Spring Security :
    Rider getCurrentRider();
    Rider createNewRider(Users users);
    boolean existsByUsersId(Long userId);
    void updateRating(Long userId, Double rating);
}
