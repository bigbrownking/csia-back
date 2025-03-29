package org.agro.agrohack.controller;

import lombok.RequiredArgsConstructor;
import org.agro.agrohack.dto.response.JwtResponse;
import org.agro.agrohack.dto.request.LoginRequest;
import org.agro.agrohack.dto.request.SignUpRequest;
import org.agro.agrohack.exception.NotFoundException;
import org.agro.agrohack.model.User;
import org.agro.agrohack.service.UserService;
import org.agro.agrohack.utils.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest
    ){
        LOGGER.warn("EMAIL IS: " + loginRequest.getEmail() + " PASSWORD IS " + loginRequest.getPassword());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during authentication: " + e.getMessage());
        }

        User userDetails = (User) userService.loadUserByUsername(loginRequest.getEmail());
        String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        String refreshToken = jwtTokenUtil.generateRefreshToken(userDetails);


        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(accessToken);
        jwtResponse.setRefreshToken(refreshToken);
        jwtResponse.setEmail(userDetails.getUsername());
        jwtResponse.setFio(userDetails.getFio());
        jwtResponse.setRole(userDetails.getRole().getName());

        return ResponseEntity.ok().body(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody SignUpRequest signupRequest
    ){
        try {
            User newUser = new User();
            newUser.setEmail(signupRequest.getEmail());
            newUser.setFio(signupRequest.getFio());
            newUser.setPassword(signupRequest.getPassword());

            userService.saveUser(newUser);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully.");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while creating the user: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid input: " + e.getMessage());
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid refresh token format");
        }

        refreshToken = refreshToken.substring(7);

        if (!jwtTokenUtil.validateRefreshToken(refreshToken)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired refresh token");
        }

        String username = jwtTokenUtil.extractUsername(refreshToken);
        User userDetails = (User) userService.loadUserByUsername(username);
        String newAccessToken = jwtTokenUtil.generateAccessToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(newAccessToken);
        jwtResponse.setRefreshToken(refreshToken);
        jwtResponse.setEmail(userDetails.getEmail());
        jwtResponse.setFio(userDetails.getFio());
        jwtResponse.setRole(userDetails.getRole().getName());

        return ResponseEntity.ok(jwtResponse);
    }

}
