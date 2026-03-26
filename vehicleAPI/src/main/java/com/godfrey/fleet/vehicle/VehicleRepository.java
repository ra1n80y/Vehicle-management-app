package com.godfrey.fleet.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    @Query(value = "SELECT id, type FROM vehicles", nativeQuery = true)
    List<Object[]> findAllRawVehicleTypes();
}
