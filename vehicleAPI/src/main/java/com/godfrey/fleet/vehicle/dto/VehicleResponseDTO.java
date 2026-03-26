package com.godfrey.fleet.vehicle.dto;

import com.godfrey.fleet.vehicle.Vehicle;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleResponseDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotBlank(message = "Name cannot be null")
    private String name;

    @NotBlank(message = "Model cannot be null")
    private String model;

    @NotNull(message = "Type cannot be null")
    private Vehicle.VehicleType type;

    @Min(value = 1900, message = "Year must be >= 1900")
    private Integer year;

    public VehicleResponseDTO() {}

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Vehicle.VehicleType getType() { return type; }
    public void setType(Vehicle.VehicleType type) { this.type = type; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
}