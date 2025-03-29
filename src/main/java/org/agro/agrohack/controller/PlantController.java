package org.agro.agrohack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.agro.agrohack.model.Plant;
import org.agro.agrohack.service.PlantService;
import org.agro.agrohack.utils.ParamUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plant")
@Tag(name = "Plant Controller",  description = "Endpoints for managing plants")
public class PlantController {
    private final PlantService plantService;

    @Operation(summary = "All plants of given difficulty")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All plants of given difficulty returned successfully"),
    })
    @GetMapping("/allPlantsOfDifficulty")
    public ResponseEntity<Page<Plant>> getAllPlantsOfDifficulty(
            @RequestParam String difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(plantService.getPlantsByDifficulty(difficulty, pageable));
    }


    @Operation(summary = "All plants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All plants returned successfully"),
    })
    @GetMapping("/allPlants")
    public ResponseEntity<Page<Plant>> getAllPlants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(plantService.getAllPlants(pageable));
    }


}
