package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.entities.Payment;
import com.pisoft.uberApp.UberApplication.entities.Ride;
import com.pisoft.uberApp.UberApplication.enums.PaymentStatus;

public interface PaymentService {

    void processPayment(Ride ride , Payment payment);

    Payment createNewPayment(Ride updateRide);

    Payment findByRide(Ride ride);

    void updatePaymentStatus(Payment payment, PaymentStatus paymentStatus);
}
