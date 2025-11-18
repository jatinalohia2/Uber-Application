package com.pisoft.uberApp.UberApplication.services.impl;

import com.pisoft.uberApp.UberApplication.entities.Ride;
import com.pisoft.uberApp.UberApplication.entities.Users;
import com.pisoft.uberApp.UberApplication.entities.Wallet;
import com.pisoft.uberApp.UberApplication.entities.WalletTransaction;
import com.pisoft.uberApp.UberApplication.enums.TransacationMethod;
import com.pisoft.uberApp.UberApplication.enums.TransacationType;
import com.pisoft.uberApp.UberApplication.exception.ResourceNotFound;
import com.pisoft.uberApp.UberApplication.repositories.WalletRepository;
import com.pisoft.uberApp.UberApplication.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet createNewWallet(Users users) {

        Wallet wallet = Wallet.builder()
                .balance(0.0)
                .users(users)
                .build();

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet addMoneyToWallet(Long usersId, double amount, TransacationType transacationType, TransacationMethod transacationMethod, Ride ride, String transactionId) {

        Wallet wallet = findByUsers(usersId);
        wallet.setBalance(wallet.getBalance() + amount);

        // wallet transaction :
        WalletTransaction walletTransaction = WalletTransaction.builder()

                .amount(amount)
                .transacationMethod(transacationMethod)
                .transacationType(transacationType)
                .wallet(wallet)
                .transactionId(transactionId)
                .ride(ride)
                .build();

        wallet.getWalletTransactions().add(walletTransaction); // add wallet trans. into wallet
        return walletRepository.save(wallet);
    }

    @Override
    public Wallet deductMoneyFromWallet(Long usersId, double amount, TransacationType transacationType, TransacationMethod transacationMethod, Ride ride, String transactionId) {

        Wallet wallet = findByUsers(usersId);
        wallet.setBalance(wallet.getBalance() - amount);

        // wallet transaction :
        WalletTransaction walletTransaction = WalletTransaction.builder()

                .amount(amount)
                .transacationMethod(transacationMethod)
                .transacationType(transacationType)
                .wallet(wallet)
                .transactionId(transactionId)
                .ride(ride)
                .build();

        wallet.getWalletTransactions().add(walletTransaction); // add wallet trans. into wallet
        return walletRepository.save(wallet);
    }


    @Override
    public Wallet findById(Long walletId) {
        return walletRepository.findById(walletId).orElseThrow(()-> new ResourceNotFound("" +
                "Wallet Not found with Id : "+walletId));
    }

    @Override
    public Wallet findByUsers(Long usersId) {
        return walletRepository.findByUsers_Id(usersId).orElseThrow(()-> new ResourceNotFound("" +
                "Wallet Not found with user id : "+usersId));
    }
}
