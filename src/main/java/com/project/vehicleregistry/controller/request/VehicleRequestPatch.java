package com.project.vehicleregistry.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.vehicleregistry.anotation.ValidBrandOnPatch;
import com.project.vehicleregistry.enums.Brand;

public record VehicleRequestPatch(
        @JsonProperty("veiculo")
        String vehicleType,

        @JsonProperty("cor")
        String color,

        @ValidBrandOnPatch(enumClass = Brand.class)
        @JsonProperty("marca")
        String brand,

        @JsonProperty("ano")
        Integer year,

        @JsonProperty("descricao")
        String description,

        @JsonProperty("vendido")
        Boolean sold) {
}