package com.pisoft.uberApp.UberApplication.entities;

import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    private Users users;

    private Double rating;

    private String licNo;

    private Boolean available;

    @Column(columnDefinition = "Geometry(Point , 4326)")
    Point currentLocation;

}
