package com.godfrey.vehicle.mapper;

import com.godfrey.vehicle.dto.VehicleCreateDTO;
import com.godfrey.vehicle.dto.VehicleUpdateDTO;
import com.godfrey.vehicle.dto.VehiclePatchDTO;
import com.godfrey.vehicle.dto.VehicleResponseDTO;
import com.godfrey.vehicle.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VehicleMapper {

    // Create → Entity
    Vehicle toEntity(VehicleCreateDTO dto);

    // Full update → modifies entity
    void updateFromDTO(VehicleUpdateDTO dto, @MappingTarget Vehicle vehicle);

    // Partial update → only updates non-null fields
    void patchFromDTO(VehiclePatchDTO dto, @MappingTarget Vehicle vehicle);

    // Entity → ResponseDTO
    VehicleResponseDTO toResponseDTO(Vehicle vehicle);

    List<VehicleResponseDTO> toResponseDTOs(List<Vehicle> vehicles);
}