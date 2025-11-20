package com.pisoft.uberApp.UberApplication.strategies.impl;

import com.pisoft.uberApp.UberApplication.entities.Driver;
import com.pisoft.uberApp.UberApplication.entities.Payment;
import com.pisoft.uberApp.UberApplication.entities.Wallet;
import com.pisoft.uberApp.UberApplication.enums.PaymentMethod;
import com.pisoft.uberApp.UberApplication.enums.TransacationMethod;
import com.pisoft.uberApp.UberApplication.enums.TransacationType;
import com.pisoft.uberApp.UberApplication.repositories.WalletRepository;
import com.pisoft.uberApp.UberApplication.services.WalletService;
import com.pisoft.uberApp.UberApplication.strategies.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CashPaymentStrategy implements PaymentStrategy  {

    private final WalletService walletService;

    // Ride : 100 cash
    // Driver : 200
    // plateform commission = 0.3
    // Driver deduct : 30 ruppe

    // Driver : 200  - 30

    @Override
    @Transactional
    public void getPaymentProcess(Payment payment) {

        Double rideAmount  = payment.getAmount();

        double deductDriverWalletAmt = rideAmount * PaymentStrategy.PLATEFORM_COMMISSIONION;

        Driver driver = payment.getRide().getDriver();

        Wallet wallet = walletService.findByUsers(driver.getUsers().getId());

        walletService.deductMoneyFromWallet(driver.getUsers().getId() , deductDriverWalletAmt , TransacationType.DEBIT , TransacationMethod.RIDE , payment.getRide() , null);

        payment.setPlatformCommissionAmt(deductDriverWalletAmt); // dirty checking ....
    }
}
