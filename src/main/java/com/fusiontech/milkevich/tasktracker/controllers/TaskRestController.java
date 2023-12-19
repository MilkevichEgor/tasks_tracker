package com.fusiontech.milkevich.tasktracker.controllers;

import com.fusiontech.milkevich.tasktracker.dto.TaskDto;
import com.fusiontech.milkevich.tasktracker.entity.Task;
import com.fusiontech.milkevich.tasktracker.repository.TaskRepository;
import com.fusiontech.milkevich.tasktracker.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Task rest controller.
 */
@Log4j2
@RestController
@RequestMapping("/tasks")
public class TaskRestController
    extends AbstractRestController<TaskService, TaskRepository, Task, TaskDto> {

  @Autowired
  private TaskService taskService;

  /**
   * Change status.
   *
   * @param request - request dto
   * @return - response dto
   */
  @Operation(summary = "Change status by id", description = "Change status by id")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "Successful operation", content = {
          @Content(schema = @Schema(implementation = TaskDto.class))
      }),
      @ApiResponse(responseCode = "500", description = "Internal server error", content = {
          @Content(schema = @Schema(implementation = TaskDto.class))
      })
  })
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
