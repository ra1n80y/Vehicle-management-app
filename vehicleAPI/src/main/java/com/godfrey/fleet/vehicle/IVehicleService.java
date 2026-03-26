package com.godfrey.fleet.vehicle;

import com.godfrey.fleet.vehicle.dto.VehicleCreateDTO;
import com.godfrey.fleet.vehicle.dto.VehiclePatchDTO;
import com.godfrey.fleet.vehicle.dto.VehicleResponseDTO;
import com.godfrey.fleet.vehicle.dto.VehicleUpdateDTO;

import java.util.List;

public interface IVehicleService {
    VehicleResponseDTO createVehicle(VehicleCreateDTO dto);
    VehicleResponseDTO updateVehicle(Long id, VehicleUpdateDTO dto);
    VehicleResponseDTO patchVehicle(Long id, VehiclePatchDTO dto);
    void deleteVehicle(Long id);
    VehicleResponseDTO getVehicle(Long id);
    List<VehicleResponseDTO> listVehicles();
}