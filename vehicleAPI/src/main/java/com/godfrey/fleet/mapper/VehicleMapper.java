package com.godfrey.fleet.mapper;

import com.godfrey.fleet.dto.vehicle.*;
import com.godfrey.fleet.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VehicleMapper {

    // When creating a new Vehicle, ignore ID (DB generates it)
    @Mapping(target = "id", ignore = true)
    Vehicle fromCreateDTO(VehicleCreateDTO dto);

    // When updating, ignore ID so it doesn't get overwritten
    @Mapping(target = "id", ignore = true)
    void updateFromDTO(VehicleUpdateDTO dto, @MappingTarget Vehicle vehicle);

    // PATCH: ignore ID and any other DB-managed fields (e.g., timestamps)
    @Mapping(target = "id", ignore = true)
    void patchFromDTO(VehiclePatchDTO dto, @MappingTarget Vehicle vehicle);

    VehicleResponseDTO toResponse(Vehicle vehicle);

//    List<VehicleResponseDTO> toResponseDTOs(List<Vehicle> vehicles);
}