package com.fusiontech.milkevich.tasktracker.controllers;

import com.fusiontech.milkevich.tasktracker.dto.UserDto;
import com.fusiontech.milkevich.tasktracker.dto.request.AuthRequest;
import com.fusiontech.milkevich.tasktracker.dto.response.JwtResponse;
import com.fusiontech.milkevich.tasktracker.dto.response.MessageResponse;
import com.fusiontech.milkevich.tasktracker.security.JwtUtils;
import com.fusiontech.milkevich.tasktracker.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication controller.
 *
 * @author Egor Milkevich
 */
@Log4j2
@RestController
@RequestMapping(path = "/auth")
public class AuthRestController {

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private UserService userService;

  /**
   * Register new user.
   *
   * @param request - AuthRequest
   * @return - ResponseEntity object containing the response
   */
  @Operation(summary = "Register a new user",
      description = "Registers a new user with the provided email and password.",
      tags = "Auth")
  @ApiResponses({
      @ApiResponse(responseCode = "200",
          description = "User registered successfully",
          content = @Content(schema = @Schema(implementation
              = MessageResponse.class), mediaType = "application/json")),
      @ApiResponse(responseCode = "400",
          description = "Error: Bad request, please check the request and try again",
          content = @Content(schema = @Schema(implementation
              = MessageResponse.class), mediaType = "application/json")),
  })
  @PostMapping("/register")
  public ResponseEntity<MessageResponse> registerUser(@RequestBody AuthRequest request) {
    try {
      userService.save(new UserDto(request.getName(), request.getPassword()));
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResponseEntity.badRequest().body(new MessageResponse("Error: " + e.getMessage()));
    }
  }

  /**
   * Login user.
   *
   * @param request - AuthRequest
   * @return - ResponseEntity object containing the response
   */
  @Operation(summary = "Authenticate a user",
      description = "Authenticate a user with the provided email and password.",
      tags = "Auth")
  @Parameters({
      @Parameter(name = "email", description = "The email of the user"),
      @Parameter(name = "password", description = "The password of the user")
  })
  @ApiResponses({
      @ApiResponse(responseCode = "200",
          description = "User authenticated successfully",
          content = @Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json")),
      @ApiResponse(responseCode = "400",
          description = "Error: Invalid credentials, please check the request",
          content = @Content(schema = @Schema(implementation = JwtResponse.class), mediaType = "application/json")),
      @ApiResponse(responseCode = "500",
          description = "Internal server error, check connection with database, and try again",
          content = @Content(schema = @Schema()))
  })
  @PostMapping("/login")
  public ResponseEntity<JwtResponse> authenticateUser(@RequestBody AuthRequest request) {
    UserDto user;
    String jwt;

    try {
      user = userService.findByName(request.getName());

      Authentication authentication = new UsernamePasswordAuthenticationToken(
          user.getUsername(),
          request.getPassword(),
          user.getAuthorities());

      SecurityContextHolder.getContext().setAuthentication(authentication);

      jwt = jwtUtils.generateJwtToken(authentication);
    } catch (Exception e) {
      log.error("Authentication error: " + e.getMessage());
      return ResponseEntity.internalServerError().body(null);
    }
    return ResponseEntity.ok(new JwtResponse(user, jwt));
  }
}
