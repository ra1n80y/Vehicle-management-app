package com.godfrey.vehicle.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VehicleResponseDTO {

    @NotNull(message = "ID cannot be null")
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Model cannot be blank")
    private String model;

    @NotBlank(message = "Type cannot be blank")
    private String type;

    @Min(value = 1900, message = "Year must be >= 1900")
    @Max(value = 2100, message = "Year must be <= 2100")
    private Integer year;

    public VehicleResponseDTO () {
    }

    // Getters & Setters
    public Long getId () { return id; }
    public void setId (Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

}