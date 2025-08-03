package com.project.vehicleregistry.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record VehicleResponse(
        Long id,
        @JsonProperty("veiculo")
        String vehicleType,
        @JsonProperty("cor")
        String color,
        @JsonProperty("marca")
        String brand,
        @JsonProperty("ano")
        Integer year,
        @JsonProperty("descricao")
        String description,
        @JsonProperty("vendido")
        Boolean sold,
        Integer decade,
        @JsonProperty("criadoEm")
        String createdAt,
        @JsonProperty("atualizadoEm")
        String updatedAt
) {
}