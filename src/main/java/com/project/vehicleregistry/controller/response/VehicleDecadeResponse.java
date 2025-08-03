package com.project.vehicleregistry.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record VehicleDecadeResponse(
        @JsonProperty("decada")
        Integer decade,
        @JsonProperty("quantidade")
        Long quantity) {
}