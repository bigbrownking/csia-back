package org.agro.agrohack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.agro.agrohack.dto.request.AddPlantRequest;
import org.agro.agrohack.dto.response.GetProfileResponse;
import org.agro.agrohack.exception.LowLevelException;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.UserPlant;
import org.agro.agrohack.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Controller",  description = "Endpoints for managing user's actions")
public class UserController {
    private final UserService userService;

    @Operation(summary = "All User's plants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All plants of given user returned successfully"),
    })
    @GetMapping("/myPlants")
    public ResponseEntity<Page<UserPlant>> getAllPlantsOfDifficulty(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) throws NotFoundException {
        return ResponseEntity.ok(userService.myPlants(email, page, size));
    }

    @Operation(summary = "Request for plant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add plant for user"),
    })
    @PostMapping("/addPlant")
    public ResponseEntity<String> createNewUserPlant(
            @RequestBody AddPlantRequest addPlantRequest

    ) throws NotFoundException, LowLevelException {
        return ResponseEntity.ok(userService.createUserPlant(addPlantRequest));
    }

    @Operation(summary = "User profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile returned successfully"),
    })
    @GetMapping("/profile")
    public ResponseEntity<GetProfileResponse> getProfile(
            @RequestParam String email
    ) throws NotFoundException {
        return ResponseEntity.ok(userService.getProfile(email));
    }
}
