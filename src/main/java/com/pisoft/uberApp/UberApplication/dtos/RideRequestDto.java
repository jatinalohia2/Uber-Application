package com.pisoft.uberApp.UberApplication.dtos;

import com.pisoft.uberApp.UberApplication.entities.Rider;
import com.pisoft.uberApp.UberApplication.enums.PaymentStatus;
import com.pisoft.uberApp.UberApplication.enums.RideRequestStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideRequestDto {

    Point pickUpLocation;

    Point dropOffLocation;

    private LocalDateTime requestedTime;

    private RiderDto riderDto;

    private PaymentStatus paymentStatus;

    private RideRequestStatus rideRequestStatus;
}
