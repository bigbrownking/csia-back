package org.agro.agrohack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.User;
import org.agro.agrohack.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "Admin Controller", description = "API for managing admin")
public class AdminController {
    private final UserService userService;

    @Operation(summary = "All users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users returned successfully"),
    })
    @GetMapping("/allUsers")
    public ResponseEntity<Page<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

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
