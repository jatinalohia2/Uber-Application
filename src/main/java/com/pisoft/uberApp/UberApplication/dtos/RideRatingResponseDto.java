package com.pisoft.uberApp.UberApplication.dtos;

import com.pisoft.uberApp.UberApplication.entities.Ride;
import com.pisoft.uberApp.UberApplication.entities.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideRatingResponseDto {

    private Long id;

    private UserDto rater; // who gives rating

    private UserDto rated;  // who receive rating

    private Double rating;

    private String feedback;

    private RideDto ride;

    private LocalDateTime createdAt;
}
