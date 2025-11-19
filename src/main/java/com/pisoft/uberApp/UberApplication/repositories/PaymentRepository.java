package com.pisoft.uberApp.UberApplication.repositories;

import com.pisoft.uberApp.UberApplication.entities.Payment;
import com.pisoft.uberApp.UberApplication.entities.Ride;
import com.pisoft.uberApp.UberApplication.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByRide(Ride ride);

}
