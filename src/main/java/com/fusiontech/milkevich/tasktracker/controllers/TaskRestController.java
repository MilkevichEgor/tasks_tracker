package com.fusiontech.milkevich.tasktracker.controllers;

import com.fusiontech.milkevich.tasktracker.dto.TaskDto;
import com.fusiontech.milkevich.tasktracker.entity.Task;
import com.fusiontech.milkevich.tasktracker.repository.TaskRepository;
import com.fusiontech.milkevich.tasktracker.services.TaskService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequestMapping("/tasks")
public class TaskRestController extends AbstractRestController<TaskService, TaskRepository, Task, TaskDto> {

  @Autowired
  private TaskService taskService;

  @PutMapping("/status")
  public ResponseEntity<TaskDto> changeStatus(@RequestBody TaskDto request) {

    try {
      TaskDto response = taskService.changeStatus(request);
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return ResponseEntity.internalServerError().build();
    }
  }
}
