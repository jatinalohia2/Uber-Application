package com.pisoft.uberApp.UberApplication.strategies;

import com.pisoft.uberApp.UberApplication.entities.Payment;

public interface PaymentStrategy {

    double PLATEFORM_COMMISSIONION = 0.3;
    void  paymentProcess(Payment payment);

}
