package com.fusiontech.milkevich.tasktracker.services;

import com.fusiontech.milkevich.tasktracker.constant.UserRole;
import com.fusiontech.milkevich.tasktracker.dto.CommentDto;
import com.fusiontech.milkevich.tasktracker.dto.TaskDto;
import com.fusiontech.milkevich.tasktracker.dto.UserDto;
import com.fusiontech.milkevich.tasktracker.entity.User;
import com.fusiontech.milkevich.tasktracker.repository.CommentRepository;
import com.fusiontech.milkevich.tasktracker.repository.TaskRepository;
import com.fusiontech.milkevich.tasktracker.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User service.
 */
@Log4j2
@Service
public class UserService extends AbstractService<User, UserDto, UserRepository> {

  @Autowired
  private TaskService taskService;

  @Autowired
  private CommentService commentService;

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private PasswordEncoder encoder;

  /**
   * Get map name for cache.
   *
   * @return - map name
   */
  @Override
  protected String getMapName() {
    return "UserMap";
  }

  @Override
  protected List<String> relatedMapNames() {
    return List.of("TaskMap", "CommentMap");
  }

  /**
   * method Save user.
   *
   * @param userDto - request UserDto
   * @return - response UserDto
   * @throws RuntimeException - if user with name already exists
   */
  @Override
  @Transactional
  public UserDto save(UserDto userDto) throws RuntimeException {
    if (userRepository.existsByName(userDto.getName())) {
      log.error("User with name " + userDto.getName() + " already exists");
      throw new RuntimeException("User with name " + userDto.getName() + " already exists");
    }
    userDto.setPassword(encoder.encode(userDto.getPassword()));
    userDto.setRole(UserRole.ROLE_USER);
    return super.save(userDto);
  }

  public UserDto findByName(String username) {
    return cache.values().stream()
        .filter(user -> user.getUsername().equals(username))
        .findFirst()
        .orElseGet(() -> {
          var dto = toDto(userRepository.findByName(username).get());
          cache.put(dto.getId(), dto);
          return dto;
        });
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

    return new UserDto(
        user.getId(),
        user.getName(),
        user.getPassword(),
        user.getRole(),
        tasks,
        comments
    );
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
    user.setPassword(userDto.getPassword());
    user.setRole(userDto.getRole());

    return user;
  }
}
