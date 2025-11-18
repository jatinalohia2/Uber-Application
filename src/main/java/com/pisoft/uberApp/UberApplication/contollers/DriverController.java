package com.pisoft.uberApp.UberApplication.contollers;

import com.pisoft.uberApp.UberApplication.dtos.DriverDto;
import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RideStartDto;
import com.pisoft.uberApp.UberApplication.dtos.RiderDto;
import com.pisoft.uberApp.UberApplication.services.DriverService;
import com.pisoft.uberApp.UberApplication.services.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final RideService rideService;

    @PostMapping("/acceptRide/{rideRequestId}")
    public RideDto acceptRide(@PathVariable Long rideRequestId){
        return driverService.acceptRide(rideRequestId);
    }

    @PostMapping("/startRide/{rideId}")
    public RideDto startRide(@PathVariable Long rideId , @RequestBody RideStartDto rideStartDto){
        return driverService.startRide(rideId , rideStartDto.getOtp());
    }

    @GetMapping("/getAllMyRides/{pageNo}")
    public List<RideDto> getAllMyRides(@RequestParam(defaultValue = "0") int pageNo){
        return driverService.getAllMyRides(pageNo).getContent();
    }

}
