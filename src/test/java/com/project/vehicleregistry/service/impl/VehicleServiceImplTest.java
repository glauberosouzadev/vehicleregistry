package com.project.vehicleregistry.service.impl;

import com.project.vehicleregistry.exceptions.NotFoundException;
import com.project.vehicleregistry.model.Vehicle;
import com.project.vehicleregistry.repository.VehicleRepository;
import factory.VehicleFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @Mock
    private VehicleRepository repository;
    @InjectMocks
    private VehicleServiceImpl service;


    @Test
    void shouldCreateVehicle() {
        // Given
        var vehicle = VehicleFactory.createVehicle(
                "Car",
                "black",
                "FORD",
                2020,
                "Economical and comfortable car",
                true);

        // When
        when(repository.save(any(Vehicle.class))).thenReturn(vehicle);
        service.createVehicle(vehicle);

        // Then
        verify(repository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void shouldFindVehicleById() {
        // Given
        var vehicle = VehicleFactory.createVehicle(
                "Car",
                "black",
                "Ford",
                2022,
                "Economical and comfortable car",
                true);
        // When
        when(repository.save(any(Vehicle.class))).thenReturn(vehicle);
        when(repository.findById(1L)).thenReturn(Optional.of(vehicle));

        service.createVehicle(vehicle);
        var vehicleById = service.findVehicleById(1L);

        //Then
        verify(repository, times(1)).findById(1L);
        Assertions.assertNotNull(vehicleById);

    }

    @Test
    void shouldUpdateVehicle() {
        // Given
        var vehicle = VehicleFactory.createVehicle(
                "Car",
                "black",
                "Ford",
                2020,
                "Economical and comfortable car",
                true);

        var updatedVehicle = VehicleFactory.updateVehicle(
                vehicle, "Truck",
                "black",
                "Ford",
                2021,
                "Economical and comfortable truck",
                false);

        // When
        when(repository.save(any(Vehicle.class))).thenReturn(vehicle);
        when(repository.findById(1L)).thenReturn(Optional.of(vehicle));

        service.createVehicle(vehicle);
        service.updateVehicle(1L, updatedVehicle);

        // Then
        verify(repository, times(2)).save(vehicle);
    }

    @Test
    void shouldDeleteVehicleById() {
        // Given
        var vehicle = VehicleFactory.createVehicle(
                "Car",
                "black",
                "HONDA",
                2022,
                "Economical and comfortable car",
                true);

        // When
        when(repository.save(any(Vehicle.class))).thenReturn(vehicle);
        when(repository.existsById(1L)).thenReturn(true);

        service.createVehicle(vehicle);
        service.deleteVehicleById(1L);
        var all = repository.findAll();

        //Then
        Assertions.assertEquals(0, all.size());
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void shouldFindAllVehiclesAvailable() {
        // Given
        var vehicle = VehicleFactory.createVehicle(
                "Car",
                "black",
                "HONDA",
                2022,
                "Economical and comfortable car",
                true);
        // When
        when(repository.findBySoldFalse()).thenReturn(List.of(vehicle));

        var allVehiclesAvailable = service.findAllNotSold();
        // Then
        verify(repository, times(1)).findBySoldFalse();
        Assertions.assertFalse(allVehiclesAvailable.isEmpty());
    }

    @Test
    void shouldFindAllVehiclesByDecade() {
        // Given
        var vehicle = VehicleFactory.createVehicleDecadeDto(2020, 2L);
        // When
        when(repository.vehiclesByFabricationDecade()).thenReturn(List.of(vehicle));
        var allVehiclesByDecade = service.vehiclesByFabricationDecade();
        // Then
        verify(repository, times(1)).vehiclesByFabricationDecade();
        Assertions.assertEquals(2020, allVehiclesByDecade.get(0).decade());
    }

    @Test
    void shouldFindAllVehiclesByBrand() {
        // Given
        var vehicle = VehicleFactory.createVehicleBrandDto("HONDA", 1L);

        // When
        when(repository.vehiclesSeparatedByBrand()).thenReturn(List.of(vehicle));

        var allVehiclesByBrand = service.vehiclesByBrand();
        // Then
        verify(repository, times(1)).vehiclesSeparatedByBrand();
        Assertions.assertEquals(1, allVehiclesByBrand.size());
        Assertions.assertEquals("HONDA", allVehiclesByBrand.get(0).brand());
    }

    @Test
    void shouldTrowExceptionIfNoFoundById() {
        var vehicleRequestPatch = VehicleFactory.createVehicleRequestPatch("Truck", "Rosa");

        Assertions.assertThrows(NotFoundException.class, () -> service.patch(1L, vehicleRequestPatch));
    }

    @Test
    void shouldTrowExceptionIfNotFoundByIdToDelete() {
        Assertions.assertThrows(NotFoundException.class, () -> service.deleteVehicleById(1L));
    }

    @Test
    void shouldApplyPatchVehicle() {
        var vehicle = VehicleFactory.createVehicle(
                "car", "black", "HONDA", 2016, "Amazing car", true);
        var vehicleRequestPatch = VehicleFactory.createVehicleRequestPatch("Truck", "Rosa");

        service.applyPatch(vehicle, vehicleRequestPatch);

        Assertions.assertEquals(vehicle.getVehicleType(), vehicleRequestPatch.vehicleType());
        Assertions.assertEquals(vehicle.getColor(), vehicleRequestPatch.color());
    }

    @Test
    void shouldFindAllVehiclesRegisteredLastWeek() {
        // Given
        var vehicle = VehicleFactory.createVehicle(
                "Car",
                "black",
                "FORD",
                2020,
                "Economical and comfortable car",
                true);

        // When
        when(repository.findByCreatedAtBetween(any(), any())).thenReturn(List.of(vehicle));
        var allVehiclesRegisteredLastWeek = service.findAllVehiclesRegisteredLastWeek();
        // Then
        verify(repository, times(1))
                .findByCreatedAtBetween(LocalDate.now().minusWeeks(1), LocalDate.now());
        Assertions.assertTrue(true,
                String.valueOf(allVehiclesRegisteredLastWeek.get(0).getCreatedAt().minusWeeks(1)));
    }
}