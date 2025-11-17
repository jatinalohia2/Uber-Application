package com.pisoft.uberApp.UberApplication.entities;

import com.pisoft.uberApp.UberApplication.enums.PaymentMethod;
import com.pisoft.uberApp.UberApplication.enums.PaymentStatus;
import com.pisoft.uberApp.UberApplication.enums.RideStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.locationtech.jts.geom.Point;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(columnDefinition = "geometry(Point , 4326)")
    private Point pickUpLocation;

    @Column(columnDefinition = "geometry(Point , 4326)")
    private Point dropOffLocation;

    @CreationTimestamp
    private LocalDateTime createdTime;

    @ManyToOne
    private Rider rider;

    @ManyToOne
    private Driver driver;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private RideStatus rideStatus;

    private String otp;

}
