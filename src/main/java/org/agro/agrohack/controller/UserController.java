package org.agro.agrohack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.agro.agrohack.config.socket.WebSocketHandler;
import org.agro.agrohack.dto.request.IndicateRequest;
import org.agro.agrohack.dto.request.SeedPlantRequest;
import org.agro.agrohack.dto.request.EditProfileRequest;
import org.agro.agrohack.dto.request.ProfileImageUploadRequest;
import org.agro.agrohack.dto.response.GetProfileResponse;
import org.agro.agrohack.exception.LowLevelException;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.Plant;
import org.agro.agrohack.model.UserPlant;
import org.agro.agrohack.model.indicators.Indicator;
import org.agro.agrohack.service.UserService;
import org.agro.agrohack.utils.ImageService;
import org.agro.agrohack.utils.LevelService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Controller",  description = "Endpoints for managing user's actions")
public class UserController {
    private final UserService userService;
    private final ImageService imageService;

    @Operation(summary = "All User's plants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All plants of given user returned successfully"),
    })
    @GetMapping("/myPlants")
    public ResponseEntity<Page<UserPlant>> getAllPlantsOfDifficulty(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size

    ) throws NotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.myPlants(email, page, size));
    }

    @Operation(summary = "Request for plant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add plant for user"),
    })
    @PostMapping("/addPlant")
    public ResponseEntity<String> createNewUserPlant(
            @RequestBody SeedPlantRequest seedPlantRequest

    ) throws NotFoundException, LowLevelException {
        return ResponseEntity.ok(userService.createUserPlant(seedPlantRequest));
    }

    @Operation(summary = "Edit user profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile edited successfully"),
    })
    @PatchMapping("/editProfile")
    public ResponseEntity<String> editProfile(
            @RequestBody EditProfileRequest editProfileRequest
            ) throws NotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.editProfile(email, editProfileRequest));

    }


    @Operation(summary = "User profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile returned successfully"),
    })
    @GetMapping("/profile")
    public ResponseEntity<GetProfileResponse> getProfile(
    ) throws NotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.getProfile(email));
    }

    @Operation(
            summary = "Add profile image",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "multipart/form-data",
                            schema = @Schema(
                                    implementation = ProfileImageUploadRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profile image uploaded successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            }
    )
    @PatchMapping("/image")
    public ResponseEntity<?> uploadProfileImage(@ModelAttribute ProfileImageUploadRequest request) throws Exception {

        String imageUrl = imageService.uploadImageToStorage(request.getImage());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        userService.uploadProfileImage(email, imageUrl);
        return ResponseEntity.ok("Profile image uploaded successfully.");
    }

    @Operation(
            summary = "Get profile image",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Image fetched successfully"),
                    @ApiResponse(responseCode = "404", description = "Image not found")
            }
    )
    @GetMapping("/image/{fileId}")
    public ResponseEntity<?> getProfileImage(@PathVariable String fileId) {
        try {
            Resource image = imageService.getImage(fileId);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(image);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(404).body("Image not found");
        }
    }

    @Operation(summary = "Indicate smth")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Indicator added successfully"),
    })
    @PatchMapping("/indicate")
    public ResponseEntity<String> indicate(
            @RequestBody IndicateRequest indicateRequest
    ) throws NotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.indicate(email, indicateRequest));

    }

    @Operation(summary = "Get indicators of plant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Indicators returned successfully"),
    })
    @GetMapping("/plant/indicators")
    public ResponseEntity<Page<Indicator>> getIndicators(
            @RequestParam String customName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) throws NotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.getIndicatorsOfUserPlant(email, customName, page, size));
    }


    @Operation(summary = "Watering a plant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Watering successfully"),
    })
    @PatchMapping("/water")
    public ResponseEntity<String> indicate(
            @RequestParam String customName
    ) throws NotFoundException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(userService.water(email, customName));

    }
}
