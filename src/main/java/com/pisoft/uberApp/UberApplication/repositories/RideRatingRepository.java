package com.pisoft.uberApp.UberApplication.repositories;

import com.pisoft.uberApp.UberApplication.entities.Payment;
import com.pisoft.uberApp.UberApplication.entities.Ride;
import com.pisoft.uberApp.UberApplication.entities.RideRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RideRatingRepository extends JpaRepository<RideRating, Long> {


    boolean existsByRideIdAndRaterId(Long rideId, Long userId);

}
