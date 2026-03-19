package com.godfrey.fleet.dto.vehicle;

import com.godfrey.fleet.model.Vehicle;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleUpdateDTO {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Model cannot be blank")
    private String model;

    @NotNull(message = "Type cannot be null")
    private Vehicle.VehicleType type;

    @Min(value = 1900, message = "Year must be >= 1900")
    private Integer year;

    public VehicleUpdateDTO() {}

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