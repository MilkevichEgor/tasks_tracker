package com.fusiontech.milkevich.tasktracker.dto;

import com.fusiontech.milkevich.tasktracker.constant.TaskStatus;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Task dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto extends AbstractDto implements Serializable {

  private Long id;
  private String title;
  private String description;
  private TaskStatus status;
  private Long userId;
  private List<CommentDto> comments;
}
