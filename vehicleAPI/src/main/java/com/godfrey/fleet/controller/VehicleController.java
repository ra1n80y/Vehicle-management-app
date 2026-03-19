package com.godfrey.fleet.controller;

import com.godfrey.fleet.dto.vehicle.*;
import com.godfrey.fleet.repository.VehicleRepository;
import com.godfrey.fleet.service.vehicle.IVehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
//@RequiredArgsConstructor
public class VehicleController {

    private final IVehicleService service;

    public VehicleController (IVehicleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {
        return ResponseEntity.ok(service.listVehicles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getVehicle(id));
    }

    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(@Valid @RequestBody VehicleCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createVehicle(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehicleUpdateDTO dto) {
        return ResponseEntity.ok(service.updateVehicle(id, dto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> patchVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehiclePatchDTO dto) {
        return ResponseEntity.ok(service.patchVehicle(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        service.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}