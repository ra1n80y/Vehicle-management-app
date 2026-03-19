package com.godfrey.fleet.dto.vehicle;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import com.godfrey.fleet.model.Vehicle;

public class VehicleCreateDTO {

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Model is required")
    private String model;

    @NotNull(message = "Type is required")
    private Vehicle.VehicleType type;

    @Min(value = 1900, message = "Year must be >= 1900")
    private Integer year;

    public VehicleCreateDTO() {}

    public VehicleCreateDTO(String name, String model, Vehicle.VehicleType type, Integer year) {
        this.name = name;
        this.model = model;
        this.type = type;
        this.year = year;
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Vehicle.VehicleType getType() { return type; }
    public void setType(Vehicle.VehicleType type) { this.type = type; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}