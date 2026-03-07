package com.godfrey.vehicle.controller;

import com.godfrey.vehicle.dto.VehicleCreateDTO;
import com.godfrey.vehicle.dto.VehiclePatchDTO;
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

    @Autowired
    private IVehicleService service;

    // Get all vehicles
    @GetMapping
    public ResponseEntity<List<Vehicle>> getAllVehicles() {
        return ResponseEntity.ok(service.fetchAllVehicles());
    }

    // Get vehicle by ID
    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.ok(service.fetchVehicleById(id));
    }

    // Create new vehicle
    @PostMapping
    public ResponseEntity<String> createVehicle(@Valid @RequestBody VehicleCreateDTO dto) {
        String result = service.createVehicle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // Full update
    @PutMapping("/{id}")
    public ResponseEntity<String> updateVehicle(@PathVariable Long id, @Valid @RequestBody Vehicle vehicle) {
        String result = service.updateVehicle(id, vehicle);
        return ResponseEntity.ok(result);
    }

    // Partial update
    @PatchMapping("/{id}")
    public ResponseEntity<String> partialUpdateVehicle(@PathVariable Long id, @Valid @RequestBody VehiclePatchDTO patch) {
        String result = service.partialUpdateVehicle(id, patch);
        if ("Not Found".equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.ok(result);
    }

    // Delete vehicle
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVehicle(@PathVariable Long id) {
        String result = service.deleteVehicleById(id);
        if ("Not Found".equals(result)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    }
}
