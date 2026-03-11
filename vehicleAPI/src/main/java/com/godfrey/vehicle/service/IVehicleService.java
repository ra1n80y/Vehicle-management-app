package com.godfrey.vehicle.service;

import com.godfrey.vehicle.dto.VehicleCreateDTO;
import com.godfrey.vehicle.dto.VehiclePatchDTO;
import com.godfrey.vehicle.dto.VehicleResponseDTO;
import com.godfrey.vehicle.dto.VehicleUpdateDTO;

import java.util.List;

public interface IVehicleService {

    // Create
    VehicleResponseDTO createVehicle(VehicleCreateDTO dto);

    // Full update (PUT)
    VehicleResponseDTO updateVehicle(Long id, VehicleUpdateDTO dto);

    // Partial update (PATCH)
    VehicleResponseDTO partialUpdateVehicle(Long id, VehiclePatchDTO dto);

    // Read
    VehicleResponseDTO fetchVehicleById(Long id);

    List<VehicleResponseDTO> fetchAllVehicles();

    // Delete
    void deleteVehicleById(Long id);
}