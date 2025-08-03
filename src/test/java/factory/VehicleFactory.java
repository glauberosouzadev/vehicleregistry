package factory;

import com.project.vehicleregistry.controller.request.VehicleRequest;
import com.project.vehicleregistry.controller.request.VehicleRequestPatch;
import com.project.vehicleregistry.dto.VehicleBrandDTO;
import com.project.vehicleregistry.dto.VehicleDecadeDTO;
import com.project.vehicleregistry.model.Vehicle;

import java.time.LocalDate;

public class VehicleFactory {

    public static VehicleRequest createVehicleRequest(
            String vehicleType, String color, String brand, int year, String description, boolean available) {
        return new VehicleRequest(
                vehicleType,
                color,
                brand,
                year,
                description,
                available
        );
    }

    public static VehicleDecadeDTO createVehicleDecadeDto(Integer decade, Long quantity) {
        return new VehicleDecadeDTO(decade, quantity);
    }

    public static VehicleBrandDTO createVehicleBrandDto(String brand, Long quantity) {
        return new VehicleBrandDTO(brand, quantity);
    }

    public static VehicleRequestPatch createVehicleRequestPatch(
            String vehicleType,
            String color,
            String brand,
            Integer year,
            String description,
            Boolean available
    ) {
        return new VehicleRequestPatch(vehicleType, color, brand, year, description, available);
    }

    public static VehicleRequestPatch createVehicleRequestPatch(
            String vehicleType,
            String color
    ) {
        return new VehicleRequestPatch(vehicleType, color, null, null, null, null);
    }


    public static Vehicle createVehicle(String vehicleType, String color, String brand, int year, String description, boolean available) {
        var vehicle = new Vehicle();
        vehicle.setVehicleType(vehicleType);
        vehicle.setColor(color);
        vehicle.setBrand(brand);
        vehicle.setvYear(year);
        vehicle.setDescription(description);
        vehicle.setSold(available);
        vehicle.setCreatedAt(LocalDate.now());
        vehicle.setUpdatedAt(LocalDate.now());
        return vehicle;
    }


    public static Vehicle updateVehicle(Vehicle vehicle, String vehicleType, String color, String brand, int year, String description, boolean available) {
        vehicle.setVehicleType(vehicleType);
        vehicle.setColor(color);
        vehicle.setBrand(brand);
        vehicle.setvYear(year);
        vehicle.setDescription(description);
        vehicle.setSold(available);
        vehicle.setUpdatedAt(LocalDate.now());
        return vehicle;
    }
}