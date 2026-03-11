package com.godfrey.vehicle.controller;

import com.godfrey.vehicle.dto.VehicleCreateDTO;
import com.godfrey.vehicle.dto.VehiclePatchDTO;
import com.godfrey.vehicle.dto.VehicleResponseDTO;
import com.godfrey.vehicle.dto.VehicleUpdateDTO;
import com.godfrey.vehicle.model.Vehicle;
import com.godfrey.vehicle.service.IVehicleService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final IVehicleService service;

    @Autowired
    public VehicleController(IVehicleService service) {
        this.service = service;
    }

    // Get all vehicles
    @GetMapping
    public ResponseEntity<List<VehicleResponseDTO>> getAllVehicles() {
        List<VehicleResponseDTO> vehicles = service.fetchAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    // Get vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> getVehicleById(@PathVariable Long id) {
        VehicleResponseDTO vehicle = service.fetchVehicleById(id);
        return ResponseEntity.ok(vehicle);
    }

    // Create new vehicle
    @PostMapping
    public ResponseEntity<VehicleResponseDTO> createVehicle(@Valid @RequestBody VehicleCreateDTO dto) {
        VehicleResponseDTO createdVehicle = service.createVehicle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicle);
    }

    // Full update
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehicleUpdateDTO dto) {

        VehicleResponseDTO updated = service.updateVehicle(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Partial update
    @PatchMapping("/{id}")
    public ResponseEntity<VehicleResponseDTO> partialUpdateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody VehiclePatchDTO patch) {

        VehicleResponseDTO updated = service.partialUpdateVehicle(id, patch);
        return ResponseEntity.ok(updated);
    }

    // Delete vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        service.deleteVehicleById(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}