package com.pisoft.uberApp.UberApplication.services;


import com.pisoft.uberApp.UberApplication.dtos.RideRatingDto;
import com.pisoft.uberApp.UberApplication.dtos.RideRatingResponseDto;

public interface RideRatingService {

    RideRatingResponseDto rate(Long rideId , RideRatingDto rideRatingDto);
}
