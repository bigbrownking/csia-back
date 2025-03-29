package org.agro.agrohack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Tag(name = "User Controller",  description = "Endpoints for managing user's actions")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Promote user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Promote successfully"),
            @ApiResponse(responseCode = "401", description = "User is already HR"),
    })
    @PutMapping("/promote")
    public ResponseEntity<String> promote(
            @RequestParam String email
    ) throws NotFoundException {
        return ResponseEntity.ok(userService.promote(email));
    }

}
