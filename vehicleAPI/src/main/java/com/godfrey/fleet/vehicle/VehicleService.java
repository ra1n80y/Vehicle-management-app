package com.godfrey.fleet.vehicle;

import com.godfrey.fleet.common.exception.ResourceNotFoundException;
import com.godfrey.fleet.security.Permissions;
import com.godfrey.fleet.security.service.CurrentUserService;
import com.godfrey.fleet.user.User;
import com.godfrey.fleet.vehicle.dto.VehicleCreateDTO;
import com.godfrey.fleet.vehicle.dto.VehiclePatchDTO;
import com.godfrey.fleet.vehicle.dto.VehicleResponseDTO;
import com.godfrey.fleet.vehicle.dto.VehicleUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VehicleService implements IVehicleService {

    public VehicleService (VehicleRepository vehicleRepository, VehicleMapper vehicleMapper, CurrentUserService currentUserService) {
        this.vehicleRepository = vehicleRepository;
        this.vehicleMapper = vehicleMapper;
        this.currentUserService = currentUserService;
    }

    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final CurrentUserService currentUserService;

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_CREATE)")
    public VehicleResponseDTO createVehicle(VehicleCreateDTO dto) {
        User currentUser = currentUserService.getCurrentUser();

        Vehicle vehicle = vehicleMapper.fromCreateDTO(dto);
        vehicle.setOwner(currentUser);

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.toResponse(savedVehicle);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_UPDATE)")
    public VehicleResponseDTO updateVehicle(Long id, VehicleUpdateDTO dto) {
        Vehicle existingVehicle = findVehicleByIdOrThrow(id);
        validateOwnershipOrOverride(existingVehicle);

        vehicleMapper.updateFromDTO(dto, existingVehicle);

        Vehicle savedVehicle = vehicleRepository.save(existingVehicle);
        return vehicleMapper.toResponse(savedVehicle);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_UPDATE)")
    public VehicleResponseDTO patchVehicle(Long id, VehiclePatchDTO dto) {
        Vehicle existingVehicle = findVehicleByIdOrThrow(id);
        validateOwnershipOrOverride(existingVehicle);

        vehicleMapper.patchFromDTO(dto, existingVehicle);

        Vehicle savedVehicle = vehicleRepository.save(existingVehicle);
        return vehicleMapper.toResponse(savedVehicle);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_DELETE)")
    public void deleteVehicle(Long id) {
        Vehicle existingVehicle = findVehicleByIdOrThrow(id);
        validateOwnershipOrOverride(existingVehicle);

        vehicleRepository.delete(existingVehicle);
    }

    private Vehicle findVehicleByIdOrThrow(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    private void validateOwnershipOrOverride(Vehicle vehicle) {
        User currentUser = currentUserService.getCurrentUser();

        boolean isOwner = vehicle.getOwner() != null
                && vehicle.getOwner().getId() != null
                && vehicle.getOwner().getId().equals(currentUser.getId());

        boolean hasOwnershipOverride = currentUserService.hasAuthority(Permissions.VEHICLE_OWNERSHIP_OVERRIDE);

        if (!isOwner && !hasOwnershipOverride) {
            throw new AccessDeniedException("You do not have permission to modify this vehicle");
        }
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_READ)")
    @Transactional(readOnly = true)
    public VehicleResponseDTO getVehicle(Long id) {
        Vehicle vehicle = findVehicleByIdOrThrow(id);
        return vehicleMapper.toResponse(vehicle);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.godfrey.fleet.security.Permissions).VEHICLE_READ)")
    @Transactional(readOnly = true)
    public List<VehicleResponseDTO> listVehicles() {
        return vehicleRepository.findAll()
                .stream()
                .map(vehicleMapper::toResponse)
                .toList();
    }
}