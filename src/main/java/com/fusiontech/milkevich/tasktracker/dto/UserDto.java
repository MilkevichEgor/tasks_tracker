package com.fusiontech.milkevich.tasktracker.dto;

import com.fusiontech.milkevich.tasktracker.constant.UserRole;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

  private Long id;
  private String name;
  private UserRole role;
  private Set<TaskDto> tasks;
}
