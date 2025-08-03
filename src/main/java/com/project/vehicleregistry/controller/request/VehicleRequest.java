package com.project.vehicleregistry.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.vehicleregistry.anotation.ValidBrand;
import com.project.vehicleregistry.enums.Brand;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record VehicleRequest(
        @JsonProperty("veiculo")
        String vehicleType,

        @JsonProperty("cor")
        @NotBlank
        String color,

        @ValidBrand(enumClass = Brand.class)
        @JsonProperty("marca")
        String brand,

        @Min(1)
        @JsonProperty("ano")
        Integer year,

        @NotBlank
        @JsonProperty("descricao")
        String description,

        @JsonProperty("vendido")
        Boolean sold
) {
}