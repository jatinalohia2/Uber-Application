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
@Builder
@Table(indexes = {
        @Index(name = "idx_driver_licNo" , columnList = "lic_no")
})
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @OneToOne
    private Users users;

    private String licNo;

    private Boolean available;

    @Column(columnDefinition = "geometry(Point , 4326)")
    private Point currentLocation;

    private Double averageRating;
    private Integer totalRating;



}
