package com.godfrey.fleet.service.vehicle;

import com.godfrey.fleet.dto.vehicle.*;
import java.util.List;

public interface IVehicleService {
    VehicleResponseDTO createVehicle(VehicleCreateDTO dto);
    VehicleResponseDTO updateVehicle(Long id, VehicleUpdateDTO dto);
    VehicleResponseDTO patchVehicle(Long id, VehiclePatchDTO dto);
    void deleteVehicle(Long id);
    VehicleResponseDTO getVehicle(Long id);
    List<VehicleResponseDTO> listVehicles();
}