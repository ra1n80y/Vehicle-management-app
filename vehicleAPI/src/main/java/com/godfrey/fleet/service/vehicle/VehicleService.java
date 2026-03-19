package com.godfrey.fleet.service.vehicle;

import com.godfrey.fleet.dto.vehicle.*;
import com.godfrey.fleet.exception.ResourceNotFoundException;
import com.godfrey.fleet.mapper.VehicleMapper;
import com.godfrey.fleet.model.Vehicle;
import com.godfrey.fleet.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
//@RequiredArgsConstructor
public class VehicleService implements IVehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public VehicleService (VehicleRepository vehicleRepository, VehicleMapper vehicleMapper) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
    }

    @Override
    public VehicleResponseDTO createVehicle(VehicleCreateDTO dto) {
        Vehicle vehicle = vehicleMapper.fromCreateDTO(dto);
        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleResponseDTO updateVehicle(Long id, VehicleUpdateDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
        vehicleMapper.updateFromDTO(dto, vehicle);
        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public VehicleResponseDTO patchVehicle(Long id, VehiclePatchDTO dto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));

        // Use MapStruct patch method to only update non-null fields
        vehicleMapper.patchFromDTO(dto, vehicle);

        // Save updated entity
        return vehicleMapper.toResponse(vehicleRepository.save(vehicle));
    }

    @Override
    public void deleteVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
        vehicleRepository.delete(vehicle);
    }

    @Override
    public VehicleResponseDTO getVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found: " + id));
        return vehicleMapper.toResponse(vehicle);
    }

    @Override
    public List<VehicleResponseDTO> listVehicles() {

        List<Object[]> rows = vehicleRepository.findAllRawVehicleTypes();
        for (Object[] row : rows) {
            System.out.println("ID=" + row[0] + ", TYPE=[" + row[1] + "]");
        }

        return vehicleRepository.findAll().stream()
                .map(vehicleMapper::toResponse)
                .collect(Collectors.toList());
    }
}