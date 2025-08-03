package com.project.vehicleregistry.mappers;

import com.project.vehicleregistry.controller.request.VehicleRequest;
import com.project.vehicleregistry.controller.request.VehicleRequestPatch;
import com.project.vehicleregistry.controller.response.VehicleBrandResponse;
import com.project.vehicleregistry.controller.response.VehicleDecadeResponse;
import com.project.vehicleregistry.controller.response.VehicleResponse;
import com.project.vehicleregistry.dto.VehicleBrandDTO;
import com.project.vehicleregistry.dto.VehicleDecadeDTO;
import com.project.vehicleregistry.model.Vehicle;

import java.time.LocalDate;
import java.util.List;

public class VehicleMapper {

    public static Vehicle requestToVehicle(VehicleRequest vehicleRequest) {
        var vehicle = new Vehicle();
        vehicle.setVehicleType(vehicleRequest.vehicleType());
        vehicle.setColor(vehicleRequest.color());
        vehicle.setBrand(vehicleRequest.brand());
        vehicle.setvYear(vehicleRequest.year());
        vehicle.setDescription(vehicleRequest.description());
        vehicle.setSold(vehicleRequest.sold());
        vehicle.setCreatedAt(LocalDate.now());
        return vehicle;
    }

    public static Vehicle requestPatchToVehicle(VehicleRequestPatch vehicleRequestPatch) {
        var vehicle = new Vehicle();
        vehicle.setVehicleType(vehicleRequestPatch.vehicleType());
        vehicle.setColor(vehicleRequestPatch.color());
        vehicle.setBrand(vehicleRequestPatch.brand());
        vehicle.setvYear(vehicleRequestPatch.year());
        vehicle.setDescription(vehicleRequestPatch.description());
        vehicle.setSold(vehicleRequestPatch.sold());
        vehicle.setCreatedAt(LocalDate.now());
        return vehicle;
    }

    public static List<VehicleResponse> entityToResponse(List<Vehicle> allVehicle) {
        return allVehicle.stream().map(VehicleMapper::toResponse).toList();
    }

    public static List<VehicleDecadeResponse> toDecadeResponse(List<VehicleDecadeDTO> vehicleDecadeDTO) {
        return vehicleDecadeDTO.stream().map(VehicleMapper::toResponse).toList();
    }

    public static VehicleDecadeResponse toResponse(VehicleDecadeDTO dto) {
        return new VehicleDecadeResponse(dto.decade(), dto.quantity());
    }

    public static List<VehicleBrandResponse> toBrandResponse(List<VehicleBrandDTO> vehicleBrandDTO) {
        return vehicleBrandDTO.stream().map(VehicleMapper::toResponse).toList();
    }

    public static VehicleBrandResponse toResponse(VehicleBrandDTO dto) {
        return new VehicleBrandResponse(dto.brand(), dto.quantity());
    }

    public static VehicleResponse toResponse(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.getId(),
                vehicle.getVehicleType(),
                vehicle.getColor(),
                vehicle.getBrand(),
                vehicle.getvYear(),
                vehicle.getDescription(),
                vehicle.getSold(),
                vehicle.getDecade(),
                vehicle.getCreatedAt().toString(),
                vehicle.getUpdatedAt() == null ? null : vehicle.getUpdatedAt().toString()
        );
    }
}