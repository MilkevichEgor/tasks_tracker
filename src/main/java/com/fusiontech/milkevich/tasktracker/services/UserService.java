package com.fusiontech.milkevich.tasktracker.services;

import com.fusiontech.milkevich.tasktracker.dto.TaskDto;
import com.fusiontech.milkevich.tasktracker.dto.UserDto;
import com.fusiontech.milkevich.tasktracker.entity.User;
import com.fusiontech.milkevich.tasktracker.repository.UserRepository;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User, UserDto, UserRepository> {

  @Autowired
  private TaskService taskService;

  @Override
  protected String getMapName() {
    return "UserMap";
  }

  @Override
  public UserDto toDto(User user) {
    Set<TaskDto> tasks = new HashSet<>();
    if (user.getTasks() != null) {
      user.getTasks().forEach(task -> tasks.add(taskService.toDto(task)));
    }

    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setRole(user.getRole());
    userDto.setTasks(tasks);
    return userDto;
  }

  @Override
  public User toEntity(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setName(userDto.getName());
    user.setRole(userDto.getRole());
    return user;
  }
}
