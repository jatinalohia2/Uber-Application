package com.pisoft.uberApp.UberApplication.contollers;

import com.pisoft.uberApp.UberApplication.dtos.RideDto;
import com.pisoft.uberApp.UberApplication.services.RiderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rider")
@RequiredArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @GetMapping("/getAllMyRides/{pageNo}")
    public List<RideDto> getAllMyRides(@RequestParam(defaultValue = "0") int pageNo){
        return riderService.getAllMyRides(pageNo).getContent();
    }
}
