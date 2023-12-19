package com.fusiontech.milkevich.tasktracker.services;

import com.fusiontech.milkevich.tasktracker.dto.CommentDto;
import com.fusiontech.milkevich.tasktracker.dto.TaskDto;
import com.fusiontech.milkevich.tasktracker.dto.UserDto;
import com.fusiontech.milkevich.tasktracker.entity.User;
import com.fusiontech.milkevich.tasktracker.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User service.
 */
@Service
public class UserService extends AbstractService<User, UserDto, UserRepository> {

  @Autowired
  private TaskService taskService;

  @Autowired
  private CommentService commentService;

  /**
   * Get map name for cache.
   *
   * @return - map name
   */
  @Override
  protected String getMapName() {
    return "UserMap";
  }

  /**
   * Convert entity to dto.
   *
   * @param user - entity user
   * @return - user dto
   */
  @Override
  public UserDto toDto(User user) {
    Set<TaskDto> tasks = new HashSet<>();
    List<CommentDto> comments = new ArrayList<>();
    if (user.getTasks() != null) {
      user.getTasks().forEach(task -> tasks.add(taskService.toDto(task)));
    }
    if (user.getComment() != null) {
      user.getComment().forEach(comment -> comments.add(commentService.toDto(comment)));
    }

    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setName(user.getName());
    userDto.setRole(user.getRole());
    userDto.setTasks(tasks);
    userDto.setComment(comments);
    return userDto;
  }

  /**
   * Convert dto to entity.
   *
   * @param userDto - user dto
   * @return - user entity
   */
  @Override
  public User toEntity(UserDto userDto) {
    User user = new User();
    user.setId(userDto.getId());
    user.setName(userDto.getName());
    user.setRole(userDto.getRole());
    return user;
  }
}
