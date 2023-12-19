package com.fusiontech.milkevich.tasktracker.dto;

import com.fusiontech.milkevich.tasktracker.constant.UserRole;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends AbstractDto implements Serializable {

  private Long id;
  private String name;
  private UserRole role;
  private Set<TaskDto> tasks;
  private List<CommentDto> comment;
}
