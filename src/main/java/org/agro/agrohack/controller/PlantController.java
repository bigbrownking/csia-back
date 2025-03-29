package org.agro.agrohack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.agro.agrohack.dto.request.ProfileImageUploadRequest;
import org.agro.agrohack.model.Plant;
import org.agro.agrohack.service.PlantService;
import org.agro.agrohack.utils.ImageService;
import org.agro.agrohack.utils.ParamUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plant")
@Tag(name = "Plant Controller",  description = "Endpoints for managing plants")
public class PlantController {
    private final PlantService plantService;
    private final ImageService imageService;

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
        return ResponseEntity.ok(plantService.getAllPlants(page, size));
    }

    @Operation(
            summary = "Add plant image",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(
                                    implementation = ProfileImageUploadRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Plant image uploaded successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            }
    )
    @PatchMapping("/image")
    public ResponseEntity<?> uploadPlantImage(@ModelAttribute ProfileImageUploadRequest request,
                                              @RequestParam String plantName) throws Exception {

        String imageUrl = imageService.uploadImageToStorage(request.getImage());

        plantService.uploadPlantImage(plantName, imageUrl);
        return ResponseEntity.ok("Plant image uploaded successfully.");
    }

    @Operation(
            summary = "Get plant images",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Images fetched successfully"),
                    @ApiResponse(responseCode = "404", description = "Images not found")
            }
    )
    @GetMapping("/images")
    public ResponseEntity<?> getPlantImages(@RequestParam List<String> fileIds) {
        try {
            Resource zipResource = imageService.getImages(fileIds);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=plant_images.zip")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(zipResource);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(404).body("Images not found");
        }
    }

}
