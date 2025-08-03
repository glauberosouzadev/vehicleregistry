package com.project.vehicleregistry.model;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vehicleType;
    private String color;
    private String brand;
    private Integer vYear; // Foi necessario colocar vYear porque year é palavra reservada do H2
    private String description;
    private Boolean sold;
    private int decade;
    private LocalDate createdAt;
    @Nullable
    private LocalDate updatedAt;

    public Vehicle() {
    }

    public Vehicle(String vehicleType, String brand, Integer vYear, String description, boolean sold) {
        this.vehicleType = vehicleType;
        this.brand = brand;
        this.vYear = vYear;
        this.description = description;
        this.sold = sold;
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public int getDecade() {
        return decade;
    }

    public void setDecade(int decade) {
        this.decade = decade;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public Integer getvYear() {
        return vYear;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getSold() {
        return sold;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public String getBrand() {
        return brand;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setvYear(Integer vYear) {
        this.vYear = vYear;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSold(Boolean available) {
        sold = available;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }
}