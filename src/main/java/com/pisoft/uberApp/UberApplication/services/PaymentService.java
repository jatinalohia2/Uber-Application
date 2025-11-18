package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.entities.Payment;
import com.pisoft.uberApp.UberApplication.entities.Ride;

public interface PaymentService {

    Payment processPayment(Ride ride);

    Payment createNewPayment(Ride updateRide);

}
