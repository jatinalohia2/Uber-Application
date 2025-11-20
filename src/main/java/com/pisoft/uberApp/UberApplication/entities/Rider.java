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
@Builder
public class Rider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    private Users users;

    @OneToMany
    @ToString.Exclude
    List<Ride> rides;

    private Double averageRating;
    private Integer totalRating;




}
