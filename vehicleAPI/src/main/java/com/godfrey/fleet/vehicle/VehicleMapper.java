package com.godfrey.fleet.vehicle;

import com.godfrey.fleet.vehicle.dto.VehicleCreateDTO;
import com.godfrey.fleet.vehicle.dto.VehiclePatchDTO;
import com.godfrey.fleet.vehicle.dto.VehicleResponseDTO;
import com.godfrey.fleet.vehicle.dto.VehicleUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface VehicleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Vehicle fromCreateDTO(VehicleCreateDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    void updateFromDTO(VehicleUpdateDTO dto, @MappingTarget Vehicle vehicle);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    void patchFromDTO(VehiclePatchDTO dto, @MappingTarget Vehicle vehicle);

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "ownerUsername", source = "owner.username")
    VehicleResponseDTO toResponse(Vehicle vehicle);
}