package com.godfrey.fleet.vehicle.dto;

import jakarta.validation.constraints.Min;
import com.godfrey.fleet.vehicle.Vehicle;
import org.springframework.lang.Nullable;

public class VehiclePatchDTO {

    @Nullable
    private String name;

    @Nullable
    private String model;

    @Nullable
    private Vehicle.VehicleType type;

    @Min(value = 1900, message = "Year must be >= 1900")
    private Integer year;

    public VehiclePatchDTO() {}

    public VehiclePatchDTO(@Nullable String name,
                           @Nullable String model,
                           @Nullable Vehicle.VehicleType type, Integer year
    ){
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