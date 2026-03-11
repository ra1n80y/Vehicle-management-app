package com.godfrey.vehicle.model;

import com.godfrey.vehicle.dto.VehicleCreateDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Column(name = "NAME")
    private String name;

    @NotBlank
    @Column(name = "MODEL")
    private String model;

    @NotBlank
    @Column(name = "TYPE")
    private String type; // SUV, sedan, etc.

    @Min(1900)
    @Max(2100)
    @Column(name = "YEAR")
    private int year;

    // Default constructor
    public Vehicle() {
        System.out.println("*Entity called*");
    }

    // Full-args constructor
    public Vehicle(Long id, String name, String model, String type, int year) {
        this.id = id;
        this.name = name;
        this.model = model;
        this.type = type;
        this.year = year;
    }

    public Vehicle (VehicleCreateDTO dto) {
        System.out.println ("Vehicle: "+dto);
    }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setModel(String model) { this.model = model; }

    public void setType(String type) { this.type = type; }

    public void setYear(int year) { this.year = year; }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getModel() {
        return this.model;
    }

    public String getType() {
        return this.type;
    }

    public int getYear() {
        return this.year;
    }


    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", type='" + type + '\'' +
                ", year=" + year +
                '}';
    }
}
