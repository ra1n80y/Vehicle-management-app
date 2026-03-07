package com.godfrey.vehicle.service;

import com.godfrey.vehicle.dto.VehicleCreateDTO;
import com.godfrey.vehicle.dto.VehiclePatchDTO;
import com.godfrey.vehicle.model.Vehicle;

import java.util.List;

public interface IVehicleService {

    // Create
    String createVehicle(VehicleCreateDTO dto);

    // Full update (PUT)
    String updateVehicle(Long id, Vehicle vehicle);

    // Partial update (PATCH)
    String partialUpdateVehicle(Long id, VehiclePatchDTO patch);

    // Read
    Vehicle fetchVehicleById(Long id);
    List<Vehicle> fetchAllVehicles();

    // Delete
    String deleteVehicleById(Long id);
}
