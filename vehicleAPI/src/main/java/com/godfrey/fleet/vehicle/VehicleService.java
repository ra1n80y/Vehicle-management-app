package com.godfrey.fleet.vehicle;

import com.godfrey.fleet.vehicle.dto.VehicleCreateDTO;
import com.godfrey.fleet.vehicle.dto.VehiclePatchDTO;
import com.godfrey.fleet.vehicle.dto.VehicleResponseDTO;
import com.godfrey.fleet.vehicle.dto.VehicleUpdateDTO;
import com.godfrey.fleet.common.exception.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService implements IVehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleService(VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_CREATE)")
    public VehicleResponseDTO createVehicle(VehicleCreateDTO dto) {
        Vehicle vehicle = vehicleMapper.fromCreateDTO(dto);
        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_UPDATE)")
    public VehicleResponseDTO updateVehicle(Long id, VehicleUpdateDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
        vehicleMapper.updateFromDTO(dto, vehicle);
        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_UPDATE)")
    public VehicleResponseDTO patchVehicle(Long id, VehiclePatchDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));

        vehicleMapper.patchFromDTO(dto, vehicle);

        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_DELETE)")
    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
        vehicleRepository.delete(vehicle);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_READ)")
    public VehicleResponseDTO getVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
        return vehicleMapper.toResponse(vehicle);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_READ)")
    public List<VehicleResponseDTO> listVehicles() {
        return vehicleRepository.findAll().stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }
}