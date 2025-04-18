package org.agro.agrohack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.agro.agrohack.dto.request.*;
import org.agro.agrohack.dto.response.GetAnswer;
import org.agro.agrohack.dto.response.GetProfileResponse;
import org.agro.agrohack.exception.LowLevelException;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.UserPlant;
import org.agro.agrohack.model.indicators.Indicator;
import org.agro.agrohack.service.UserService;
import org.agro.agrohack.utils.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.util.Arrays;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Controller",  description = "Endpoints for managing user's actions")
public class UserController {
    private final UserService userService;
    private final ImageService imageService;
    private final RestTemplate restTemplate;
    private final String fastApiUrl = "http://localhost:8000/askSmart/";

    private GetAnswer fetchDocumentScoresWithTag(AskQuestion askQuestion) {
        try {
            ResponseEntity<GetAnswer> modelResponse = restTemplate.postForEntity(fastApiUrl, askQuestion, GetAnswer.class);
            if (modelResponse.getBody() == null) {
                log.error("❌ Received null response from FastAPI predict endpoint");
                return new GetAnswer();
            }

            log.info("✅ Received response from FastAPI: {}", modelResponse.getBody());
            return modelResponse.getBody();
        } catch (Exception e) {
            log.error("❌ Error while fetching document scores: {}", e.getMessage(), e);
            return new GetAnswer();
        }
    }

    @Operation(summary = "Request for plant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Add plant for user"),
    })
    @PostMapping("/model")
    public ResponseEntity<GetAnswer> model(@RequestBody AskQuestion request) {
        log.error("Request is" + request.getQuestion());
        return ResponseEntity.ok(fetchDocumentScoresWithTag(request));
    }

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


    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found...")
    })
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String id) throws NotFoundException {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

}
