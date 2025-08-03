package com.project.vehicleregistry.controller;

import com.project.vehicleregistry.controller.request.VehicleRequest;
import com.project.vehicleregistry.controller.request.VehicleRequestPatch;
import com.project.vehicleregistry.controller.response.VehicleBrandResponse;
import com.project.vehicleregistry.controller.response.VehicleDecadeResponse;
import com.project.vehicleregistry.controller.response.VehicleResponse;
import com.project.vehicleregistry.mappers.VehicleMapper;
import com.project.vehicleregistry.service.VehicleService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/veiculos")
public class VehicleController {


    private VehicleService service;

    @Autowired
    public VehicleController(VehicleService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody VehicleRequest vehicleRequest) {
        var vehicle = VehicleMapper.requestToVehicle(vehicleRequest);
        service.createVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponse> findById(@PathVariable Long id) {
        var vehicleById = service.findVehicleById(id);
        var response = VehicleMapper.toResponse(vehicleById);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
     * Por questão de tempo foi usado o findAll sem paginação.
     * Para um bom desempenho a paginação deve ser adicionada posteriormente.
     * */
    @GetMapping
    public ResponseEntity<List<VehicleResponse>> findAllByBrandYearColor(
            @RequestParam(name = "marca") @Nullable String brand,
            @RequestParam(name = "ano") @Nullable String year,
            @RequestParam(name = "cor") @Nullable String color) {
        var listByBrandYearColor = service.findByBrandYearAndColor(brand, year, color);
        var vehicleResponses = VehicleMapper.entityToResponse(listByBrandYearColor);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleResponses);
    }

    @GetMapping("/not-sold")
    public ResponseEntity<List<VehicleResponse>> findAllNotSold() {
        var allNotSold = service.findAllNotSold();
        var vehicleResponses = VehicleMapper.entityToResponse(allNotSold);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleResponses);
    }

    @GetMapping("/by-last-week")
    public ResponseEntity<List<VehicleResponse>> findAllVehiclesRegisteredLastWeek() {
        var allVehiclesRegisteredLastWeek = service.findAllVehiclesRegisteredLastWeek();
        var vehicleResponses = VehicleMapper.entityToResponse(allVehiclesRegisteredLastWeek);
        return ResponseEntity.status(HttpStatus.OK).body(vehicleResponses);
    }

    @GetMapping("/by-decade")
    public ResponseEntity<List<VehicleDecadeResponse>> findAllByDecade() {
        var vehiclesByDecade = service.vehiclesByFabricationDecade();
        var decadeResponse = VehicleMapper.toDecadeResponse(vehiclesByDecade);
        return ResponseEntity.status(HttpStatus.OK).body(decadeResponse);
    }

    @GetMapping("/by-brand")
    public ResponseEntity<List<VehicleBrandResponse>> findAllByBrand() {
        var vehicleBrandDTOS = service.vehiclesByBrand();
        var brandResponse = VehicleMapper.toBrandResponse(vehicleBrandDTOS);
        return ResponseEntity.status(HttpStatus.OK).body(brandResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody VehicleRequest request) {
        var vehicle = VehicleMapper.requestToVehicle(request);
        service.updateVehicle(id, vehicle);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable Long id, @Valid @RequestBody VehicleRequestPatch request) {
        service.patch(id, request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.deleteVehicleById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}