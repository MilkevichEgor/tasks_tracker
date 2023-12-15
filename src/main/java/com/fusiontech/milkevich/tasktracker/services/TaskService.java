package com.fusiontech.milkevich.tasktracker.services;

import com.fusiontech.milkevich.tasktracker.dto.TaskDto;
import com.fusiontech.milkevich.tasktracker.entity.Task;
import com.fusiontech.milkevich.tasktracker.entity.User;
import com.fusiontech.milkevich.tasktracker.repository.TaskRepository;
import com.fusiontech.milkevich.tasktracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService extends AbstractService<Task, TaskDto, TaskRepository> {

  @Autowired
  private UserRepository userRepository;

  @Transactional
  public TaskDto changeStatus(TaskDto taskDto) {
    Task task = repository.findById(taskDto.getId()).get();
    task.setStatus(taskDto.getStatus());
    return toDto(repository.save(task));
  }

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

  @Override
  protected String getMapName() {
    return "TaskMap";
  }

  @Override
  public TaskDto toDto(Task task) {
    return new TaskDto(
        task.getId(),
        task.getTitle(),
        task.getDescription(),
        task.getStatus(),
        task.getUser() != null ? task.getUser().getId() : null
    );
  }
}
