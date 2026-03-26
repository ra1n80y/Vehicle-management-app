package com.godfrey.fleet.vehicle;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.Year;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    public enum VehicleType {
        SUV, SEDAN, HATCHBACK
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Column(name = "NAME", nullable = false)
    private String name;

    @NotBlank
    @Column(name = "MODEL", nullable = false)
    private String model;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private VehicleType type;

    @Min(1900)
    @Max(Year.MAX_VALUE) // We will validate manually in setter
    @Column(name = "YEAR", nullable = false)
    private int year;

    // Default constructor
    public Vehicle() {
        System.out.println("*Entity called*");
    }

    // Full-args constructor
    public Vehicle(Long id, String name, String model, VehicleType type, int year) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.type = type;
        setYear(year); // Use setter to enforce max
    }

    // Getters / Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public VehicleType getType() { return type; }
    public void setType(VehicleType type) { this.type = type; }

    public int getYear() { return year; }
    public void setYear(int year) {
        int currentYear = Year.now().getValue();
        if (year > currentYear) {
            throw new IllegalArgumentException("Year cannot be in the future");
        }
        this.year = year;
    }
}