package com.project.vehicleregistry.service;

import com.project.vehicleregistry.controller.request.VehicleRequestPatch;
import com.project.vehicleregistry.dto.VehicleBrandDTO;
import com.project.vehicleregistry.dto.VehicleDecadeDTO;
import com.project.vehicleregistry.model.Vehicle;

import java.util.List;

public interface VehicleService {
    void createVehicle(Vehicle vehicle);

    List<Vehicle> findAll();

    Vehicle findVehicleById(Long id);

    void deleteVehicleById(Long id);

    void updateVehicle(Long id, Vehicle vehicle);

    void patch(Long id, VehicleRequestPatch vehicleRequestPatch);

    List<Vehicle> findByBrandYearAndColor(String brand, String year, String color);

    List<Vehicle> findAllNotSold();

    List<Vehicle> findAllVehiclesRegisteredLastWeek();

    List<VehicleDecadeDTO> vehiclesByFabricationDecade();

    List<VehicleBrandDTO> vehiclesByBrand();
}