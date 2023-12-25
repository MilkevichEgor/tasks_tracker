package com.fusiontech.milkevich.tasktracker.dto.response;

import com.fusiontech.milkevich.tasktracker.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Jwt response.
 */
@Data
@AllArgsConstructor
public class JwtResponse {

  private final UserDto user;
  private String token;
}
