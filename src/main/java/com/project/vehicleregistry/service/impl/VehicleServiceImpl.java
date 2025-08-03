package com.project.vehicleregistry.service.impl;

import com.project.vehicleregistry.controller.request.VehicleRequestPatch;
import com.project.vehicleregistry.dto.VehicleBrandDTO;
import com.project.vehicleregistry.dto.VehicleDecadeDTO;
import com.project.vehicleregistry.exceptions.NotFoundException;
import com.project.vehicleregistry.model.Vehicle;
import com.project.vehicleregistry.repository.VehicleRepository;
import com.project.vehicleregistry.service.VehicleService;
import com.project.vehicleregistry.utils.DecadeFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository repository;

    @Override
    public void createVehicle(Vehicle vehicle) {
        vehicle.setDecade(DecadeFinder.find(vehicle.getvYear()));
        repository.save(vehicle);
    }

    @Override
    public List<Vehicle> findAll() {
        return repository.findAll();
    }

    @Override
    public List<VehicleDecadeDTO> vehiclesByFabricationDecade() {
        return repository.vehiclesByFabricationDecade();
    }

    @Override
    public List<VehicleBrandDTO> vehiclesByBrand() {
        return repository.vehiclesSeparatedByBrand();
    }

    @Override
    public Vehicle findVehicleById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle not found with id: " + id));
    }

    @Override
    public void updateVehicle(Long id, Vehicle vehicle) {
        var vehicleToUpdate = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Vehicle not found with id: " + id));

        vehicleToUpdate.setVehicleType(vehicle.getVehicleType());
        vehicleToUpdate.setColor(vehicle.getColor());
        vehicleToUpdate.setBrand(vehicle.getBrand());
        vehicleToUpdate.setvYear(vehicle.getvYear());
        vehicleToUpdate.setDescription(vehicle.getDescription());
        vehicleToUpdate.setSold(vehicle.getSold());
        vehicleToUpdate.setUpdatedAt(LocalDate.now());
        vehicleToUpdate.setDecade(DecadeFinder.find(vehicle.getvYear()));

        repository.save(vehicleToUpdate);
    }

    @Override
    public void patch(Long id, VehicleRequestPatch vehicleRequestPatch) {
        var vehicleToPatch = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Vehicle not found with id: " + id));
        applyPatch(vehicleToPatch, vehicleRequestPatch);
        repository.save(vehicleToPatch);
    }

    public void applyPatch(Vehicle vehicle, VehicleRequestPatch request) {
        if (request.vehicleType() != null) {
            vehicle.setVehicleType(request.vehicleType());
        }
        if (request.color() != null) {
            vehicle.setColor(request.color());
        }
        if (request.brand() != null) {
            vehicle.setBrand(request.brand());
        }
        if (request.year() != null) {
            vehicle.setvYear(request.year());
            vehicle.setDecade(DecadeFinder.find(vehicle.getvYear()));
        }
        if (request.description() != null) {
            vehicle.setDescription(request.description());
        }
        if (request.sold() != null) {
            vehicle.setSold(request.sold());
        }
        vehicle.setUpdatedAt(LocalDate.now());
    }

    @Override
    public void deleteVehicleById(Long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Vehicle not found with id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public List<Vehicle> findByBrandYearAndColor(String brand, String year, String color) {
        if (brand != null) {
            brand = brand.toUpperCase();
        }
        return repository.findByBrandYearAndColor(brand, year, color);
    }

    @Override
    public List<Vehicle> findAllNotSold() {
        return repository.findBySoldFalse();
    }

    @Override
    public List<Vehicle> findAllVehiclesRegisteredLastWeek() {
        return repository.findByCreatedAtBetween(
                LocalDate.now().minusWeeks(1),
                LocalDate.now());
    }
}