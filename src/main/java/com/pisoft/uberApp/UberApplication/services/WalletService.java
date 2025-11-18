package com.pisoft.uberApp.UberApplication.services;

import com.pisoft.uberApp.UberApplication.entities.Ride;
import com.pisoft.uberApp.UberApplication.entities.Users;
import com.pisoft.uberApp.UberApplication.entities.Wallet;
import com.pisoft.uberApp.UberApplication.enums.TransacationMethod;
import com.pisoft.uberApp.UberApplication.enums.TransacationType;

public interface WalletService {

    Wallet createNewWallet(Users  users);
    Wallet addMoneyToWallet(Long  usersId , double amount  , TransacationType transacationType , TransacationMethod transacationMethod , Ride ride , String transactionId);
    Wallet deductMoneyFromWallet(Long  usersId , double amount  , TransacationType transacationType , TransacationMethod transacationMethod , Ride ride , String transactionId);

    Wallet findById(Long walletId);

    Wallet findByUsers(Long  usersId);

}
