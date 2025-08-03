package com.project.vehicleregistry.repository;

import com.project.vehicleregistry.dto.VehicleBrandDTO;
import com.project.vehicleregistry.dto.VehicleDecadeDTO;
import com.project.vehicleregistry.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    @Query(
            value = "SELECT * FROM VEHICLE WHERE " +
                    "(:brand IS NULL OR brand = :brand) AND " +
                    "(:year IS NULL OR v_year = :year) AND " +
                    "(:color IS NULL OR color = :color)", nativeQuery = true
    )
    List<Vehicle> findByBrandYearAndColor(
            @Param("brand") String brand,
            @Param("year") String year,
            @Param("color") String color);

    @Query(
            "SELECT new com.project.vehicleregistry.dto.VehicleDecadeDTO(v.decade, COUNT(v)) FROM Vehicle v GROUP BY v.decade"
    )
    List<VehicleDecadeDTO> vehiclesByFabricationDecade();

    @Query(
            "SELECT new com.project.vehicleregistry.dto.VehicleBrandDTO(v.brand, COUNT(v)) FROM Vehicle v GROUP BY v.brand"
    )
    List<VehicleBrandDTO> vehiclesSeparatedByBrand();

    List<Vehicle> findBySoldFalse();

    List<Vehicle> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);
}