package com.pisoft.uberApp.UberApplication.entities;

import com.pisoft.uberApp.UberApplication.enums.TransacationMethod;
import com.pisoft.uberApp.UberApplication.enums.TransacationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private Double amount;

    @ManyToOne
    private Wallet wallet;

    @Enumerated(EnumType.STRING)
    private TransacationType transacationType;

    @Enumerated(EnumType.STRING)
    private TransacationMethod transacationMethod;

    @OneToOne
    private Ride ride;

    private String transactionId;

    @CreationTimestamp
    private LocalDateTime localDateTime;


}
