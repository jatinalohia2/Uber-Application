package com.pisoft.uberApp.UberApplication.strategies;

import com.pisoft.uberApp.UberApplication.entities.Rider;
import com.pisoft.uberApp.UberApplication.services.DriverMatchingStrategy;
import com.pisoft.uberApp.UberApplication.services.RideFareCalculationStrategy;
import com.pisoft.uberApp.UberApplication.services.RiderService;
import com.pisoft.uberApp.UberApplication.services.impl.DriverMatchingHighestRatedDriverStrategy;
import com.pisoft.uberApp.UberApplication.services.impl.DriverMatchingNearestDriverStrategy;
import com.pisoft.uberApp.UberApplication.services.impl.RideFareDefaultCalculationStrategy;
import com.pisoft.uberApp.UberApplication.services.impl.RideFareSurgePricingStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class RideStrategy {

    private final DriverMatchingNearestDriverStrategy driverMatchingNearestDriverStartegy;
    private final DriverMatchingHighestRatedDriverStrategy driverMatchingHighestRatedDriverStartegy;


    private final RideFareDefaultCalculationStrategy rideFareDefaultCalculationStrategy;
    private final RideFareSurgePricingStrategy rideFareSurgePricingStrategy;

    public DriverMatchingStrategy findMatchingDriver(double rating){

        if (rating >= 4){
            // high
            return  driverMatchingHighestRatedDriverStartegy;
        }else{
            // near
            return driverMatchingNearestDriverStartegy;
        }
    }

    public RideFareCalculationStrategy rideFareCalculationStrategy(){

        //  6PM - 9PM ( price 20 )

        LocalTime surgeStartTime = LocalTime.of(18 , 0); // 6pm
        LocalTime surgeEndTime = LocalTime.of(21 , 0);  // 9pm


        LocalTime currentTime = LocalTime.now();

        boolean isSurgeTime = currentTime.isAfter(surgeStartTime) && currentTime.isBefore(surgeEndTime);

        if (isSurgeTime){
            // surge pricing
            return rideFareSurgePricingStrategy;
        }else {
            // default
            return rideFareDefaultCalculationStrategy;
        }
    }
}
