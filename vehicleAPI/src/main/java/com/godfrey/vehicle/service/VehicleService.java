package com.godfrey.vehicle.service;

import com.godfrey.vehicle.dto.VehicleCreateDTO;
import com.godfrey.vehicle.dto.VehiclePatchDTO;
import com.godfrey.vehicle.dto.VehicleResponseDTO;
import com.godfrey.vehicle.dto.VehicleUpdateDTO;
import com.godfrey.vehicle.mapper.VehicleMapper;
import com.godfrey.vehicle.model.Vehicle;
import com.godfrey.vehicle.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class VehicleService implements IVehicleService {

    private final VehicleRepository repo;
    private final VehicleMapper mapper;

    @Autowired
    public VehicleService(VehicleRepository repo, VehicleMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public VehicleResponseDTO createVehicle(VehicleCreateDTO dto) {

        Vehicle vehicle = mapper.toEntity(dto);

        Vehicle saved = repo.save(vehicle);

        return mapper.toResponseDTO(saved);
    }

    @Override
    public VehicleResponseDTO updateVehicle(Long id, VehicleUpdateDTO dto) {

        Vehicle vehicle = repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        mapper.updateFromDTO(dto, vehicle);

        Vehicle updated = repo.save(vehicle);

        return mapper.toResponseDTO(updated);
    }

    @Override
    public VehicleResponseDTO partialUpdateVehicle(Long id, VehiclePatchDTO patch) {

        Vehicle vehicle = repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        mapper.patchFromDTO(patch, vehicle);

        Vehicle saved = repo.save(vehicle);

        return mapper.toResponseDTO(saved);
    }

    @Override
    public VehicleResponseDTO fetchVehicleById(Long id) {

        Vehicle vehicle = repo.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));

        return mapper.toResponseDTO(vehicle);
    }

    @Override
    public List<VehicleResponseDTO> fetchAllVehicles() {

        return repo.findAll()
                .stream()
                .map(mapper::toResponseDTO)
                .toList();
    }

    @Override
    public void deleteVehicleById(Long id) {

        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        repo.deleteById(id);
    }
}
