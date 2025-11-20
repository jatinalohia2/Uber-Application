package com.pisoft.uberApp.UberApplication.contollers;

import com.pisoft.uberApp.UberApplication.dtos.RideRatingDto;
import com.pisoft.uberApp.UberApplication.dtos.RideRatingResponseDto;
import com.pisoft.uberApp.UberApplication.services.RideRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
public class RIdeRatingController {

    private final RideRatingService rideRatingService;

    @PostMapping("/{rideId}/rate")
    public RideRatingResponseDto rideRating(@PathVariable Long rideId , @RequestBody RideRatingDto  rideRatingDto){
        return rideRatingService.rate(rideId , rideRatingDto);
    }

}
