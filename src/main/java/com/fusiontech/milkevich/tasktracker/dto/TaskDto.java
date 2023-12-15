package com.fusiontech.milkevich.tasktracker.dto;

import com.fusiontech.milkevich.tasktracker.constant.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

  private Long id;
  private String title;
  private String description;
  private TaskStatus status;
  private Long userId;

}
