package com.fusiontech.milkevich.tasktracker.services;

import com.fusiontech.milkevich.tasktracker.dto.CommentDto;
import com.fusiontech.milkevich.tasktracker.dto.TaskDto;
import com.fusiontech.milkevich.tasktracker.entity.Task;
import com.fusiontech.milkevich.tasktracker.entity.User;
import com.fusiontech.milkevich.tasktracker.repository.TaskRepository;
import com.fusiontech.milkevich.tasktracker.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Task service.
 */
@Service
public class TaskService extends AbstractService<Task, TaskDto, TaskRepository> {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CommentService commentService;

  /**
   * Change status.
   *
   * @param taskDto - task dto
   * @return - task dto
   */
  @Transactional
  public TaskDto changeStatus(TaskDto taskDto) {
    Task task = repository.findById(taskDto.getId()).get();
    task.setStatus(taskDto.getStatus());
    return toDto(repository.save(task));
  }

  /**
   * Convert dto to entity.
   *
   * @param dto - task dto
   * @return - task entity
   */
  @Override
  public Task toEntity(TaskDto dto) {
    Task task = new Task();
    task.setId(dto.getId());
    task.setTitle(dto.getTitle());
    task.setDescription(dto.getDescription());
    task.setStatus(dto.getStatus());

    if (dto.getUserId() != null) {
      User user = userRepository.findById(dto.getUserId()).get();
      task.setUser(user);
    }

    return task;
  }

  /**
   * Get map name for cache.
   *
   * @return - map name
   */
  @Override
  protected String getMapName() {
    return "TaskMap";
  }

  @Override
  protected List<String> relatedMapNames() {
    return List.of("UserMap", "CommentMap");
  }

  /**
   * Convert entity to dto.
   *
   * @param task - entity task
   * @return - task dto
   */
  @Override
  public TaskDto toDto(Task task) {

    List<CommentDto> comments = new ArrayList<>();
    if (task.getComment() != null) {
      task.getComment().forEach(comment -> comments.add(commentService.toDto(comment)));
    }
    return new TaskDto(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getStatus(),
        task.getUser() != null ? task.getUser().getId() : null,
        comments
    );
  }
}
