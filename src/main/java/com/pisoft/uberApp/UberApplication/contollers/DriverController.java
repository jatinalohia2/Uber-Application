package com.pisoft.uberApp.UberApplication.contollers;

import com.pisoft.uberApp.UberApplication.dtos.DriverDto;
import com.pisoft.uberApp.UberApplication.dtos.DriverOnboardDto;
import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.dtos.RideStartDto;
import com.pisoft.uberApp.UberApplication.services.AuthService;
import com.pisoft.uberApp.UberApplication.services.DriverService;
import com.pisoft.uberApp.UberApplication.services.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
@RequiredArgsConstructor
public class DriverController {

    private final DriverService driverService;
    private final RideService rideService;
    private final AuthService authService;

    @PostMapping("/acceptRide/{rideRequestId}")
    public ResponseEntity<RideDto> acceptRide(@PathVariable Long rideRequestId){
        RideDto rideDto = driverService.acceptRide(rideRequestId);
        return new ResponseEntity<>(rideDto , HttpStatus.CREATED);
    }

    @PostMapping("/startRide/{rideId}")
    public ResponseEntity<RideDto> startRide(@PathVariable Long rideId , @RequestBody RideStartDto rideStartDto){
        RideDto rideDto = driverService.startRide(rideId, rideStartDto.getOtp());
        return new ResponseEntity<>(rideDto , HttpStatus.CREATED);
    }

    @PostMapping("/endRide/{rideId}")
    public ResponseEntity<RideDto> endRide(@PathVariable Long rideId){
        RideDto rideDto = driverService.endRide(rideId);
        return ResponseEntity.ok(rideDto);
    }

    @GetMapping("/getAllMyRides/{pageNo}")
    public ResponseEntity<List<RideDto>> getAllMyRides(@RequestParam(defaultValue = "0") int pageNo){
        return ResponseEntity.ok(driverService.getAllMyRides(pageNo).getContent());
    }

    @GetMapping("/getMyProfile")
    public ResponseEntity<DriverDto> getMyProfile(){
        return ResponseEntity.ok(driverService.getMyProfile());
    }

    @PostMapping("/onBoardNewDriver/{userId}")
    public ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId , @RequestBody DriverOnboardDto driverOnboardDto){
        DriverDto driverDto = authService.onBoardNewDriver(userId, driverOnboardDto);
        return new ResponseEntity<>(driverDto , HttpStatus.CREATED);
    }

}
