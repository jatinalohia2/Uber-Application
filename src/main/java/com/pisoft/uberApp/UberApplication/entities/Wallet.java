package com.pisoft.uberApp.UberApplication.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    private User user;

    private Double balance;

    @OneToMany
    @ToString.Exclude
    List<WalletTransaction> walletTransactions;

}
