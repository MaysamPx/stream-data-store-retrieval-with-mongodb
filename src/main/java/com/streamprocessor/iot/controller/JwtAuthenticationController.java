package com.streamprocessor.iot.controller;

import com.streamprocessor.iot.authentication.JwtRequest;
import com.streamprocessor.iot.authentication.JwtResponse;
import com.streamprocessor.iot.authentication.JwtUtil;
import com.streamprocessor.iot.service.IoTUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication", description = "Authentication Endpoint for the JWT Token")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final IoTUserDetailsService userDetailsService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager,
                                       JwtUtil jwtUtil,
                                       IoTUserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authenticate user and generate JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated and generated JWT token",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JwtResponse.class)) }),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content)
    })
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}