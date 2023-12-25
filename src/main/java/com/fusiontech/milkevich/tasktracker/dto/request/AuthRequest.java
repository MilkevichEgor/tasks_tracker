package com.fusiontech.milkevich.tasktracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Authentication request.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

  @NotBlank
  private String name;

  @NotBlank
  private String password;
}
