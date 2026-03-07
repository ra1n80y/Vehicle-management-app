package com.godfrey.vehicle.service;

import com.godfrey.vehicle.dto.VehicleCreateDTO;
import com.godfrey.vehicle.dto.VehiclePatchDTO;
import com.godfrey.vehicle.model.Vehicle;
import com.godfrey.vehicle.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class VehicleService implements IVehicleService {

    @Autowired
    private VehicleRepository repo;

    @Override
    public String createVehicle(VehicleCreateDTO dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setName(dto.getName());
        vehicle.setModel(dto.getModel());
        vehicle.setType(dto.getType());
        vehicle.setYear(dto.getYear());
        repo.save(vehicle);
        return "Vehicle registered";
    }

    @Override
    public String updateVehicle(Long id, Vehicle vehicle) {
        Optional<Vehicle> optional = repo.findById(id);
        if (optional.isPresent()) {
            vehicle.setId(id);
            repo.save(vehicle);
            return "Vehicle " + id + " updated";
        } else {
            return id + " Not Found";
        }
    }

    @Override
    public String partialUpdateVehicle(Long id, VehiclePatchDTO patch) {
        Optional<Vehicle> optional = repo.findById(id);
        if (optional.isEmpty()) {
            return "Not Found";
        }

        Vehicle vehicle = optional.get();

        if (patch.getName() != null) vehicle.setName(patch.getName());
        if (patch.getModel() != null) vehicle.setModel(patch.getModel());
        if (patch.getType() != null) vehicle.setType(patch.getType());
        if (patch.getYear() != null) vehicle.setYear(patch.getYear());

        repo.save(vehicle);
        return "Vehicle: "+id+" Updated";
    }

    @Override
    public Vehicle fetchVehicleById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NoSuchElementException ("Vehicle not found with id: " + id));
    }

    @Override
    public List<Vehicle> fetchAllVehicles() {
        return repo.findAll();
    }

    @Override
    public String deleteVehicleById(Long id) {
        if (!repo.existsById(id)) {
            return "Not Found";
        }
        repo.deleteById(id);
        return "Deleted";
    }
}
