package com.pisoft.uberApp.UberApplication.repositories;

import com.pisoft.uberApp.UberApplication.entities.Driver;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    // ST_Distance(point1, point2) -> cal. distance
    // ST_DWithin(point1, 10000) // -> cal. within 10 km area


    @Query(value = "select d.* , ST_Distance(d.currentLocation ,  :pickUpLocation) AS distance " +
            "FROM driver d " +
            "WHERE d.available = true AND ST_DWithin(d.currentLocation :pickUpLocation , 1000) " +
            "ORDER BY distance" +
            "LIMIT 5 ", nativeQuery = true)
    List<Driver> findNearestDriver(Point pickUpLocation);
}
