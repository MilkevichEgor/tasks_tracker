package com.fusiontech.milkevich.tasktracker.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Comment dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto extends AbstractDto implements Serializable {

  private Long id;
  private String text;
  private Long userId;
  private Long taskId;
}
