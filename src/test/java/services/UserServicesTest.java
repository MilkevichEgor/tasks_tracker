package services;

import com.fusiontech.milkevich.tasktracker.constant.TaskStatus;
import com.fusiontech.milkevich.tasktracker.constant.UserRole;
import com.fusiontech.milkevich.tasktracker.dto.TaskDto;
import com.fusiontech.milkevich.tasktracker.dto.UserDto;
import com.fusiontech.milkevich.tasktracker.entity.Task;
import com.fusiontech.milkevich.tasktracker.entity.User;
import com.fusiontech.milkevich.tasktracker.repository.TaskRepository;
import com.fusiontech.milkevich.tasktracker.repository.UserRepository;
import com.fusiontech.milkevich.tasktracker.services.TaskService;
import com.fusiontech.milkevich.tasktracker.services.UserService;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = {UserRepository.class, UserService.class})
@ComponentScan(basePackages = {"com.fusiontech.milkevich.tasktracker"})
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableConfigurationProperties
@ActiveProfiles("test")
class UserServicesTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserService userService;

  @Autowired
  private TaskService taskService;

  @BeforeEach
  void setup() {
    User user = createUser("test");
    User user2 = createUser("test2");
    Task task = createTask("test");
    Task task2 = createTask("test2");

    taskRepository.save(task);
    taskRepository.save(task2);

    userRepository.save(user);
    userRepository.save(user2);
  }

  public User createUser(String name) {
    User user = new User();
    user.setName("test");
    user.setRole(UserRole.ROLE_USER);
    user.setTasks(List.of());
    return user;
  }

  public Task createTask(String name) {
    Task task = new Task();
    task.setTitle(name);
    task.setDescription("test");
    task.setStatus(TaskStatus.ACTIVE);
    return task;
  }

  @Test
  @DirtiesContext
  public void getUserTest() {
    Long id = 1L;

    User user = userRepository.findById(id).get();
    Assertions.assertEquals("test", user.getName());
    Assertions.assertEquals(UserRole.ROLE_USER, user.getRole());
    Assertions.assertEquals(1, user.getId());
  }

  @Test
  @DirtiesContext
  public void getAllUsersTest() {
    List<User> users = userRepository.findAll();
    Assertions.assertEquals(2, users.size());
  }

  @Test
  @DirtiesContext
  public void saveUserTest() {
    User user = new User();
    user.setName("test");
    user.setRole(UserRole.ROLE_USER);
    user.setTasks(List.of());
    userRepository.save(user);
    Assertions.assertEquals(3, userRepository.findAll().size());

  }

  @Test
  @DirtiesContext
  public void deleteUserTest() {
    Long id = 2L;
    userRepository.deleteById(id);
    Assertions.assertEquals(1, userRepository.findAll().size());
  }

  @Test
  @DirtiesContext
  public void toDtoTest() {
    Long id = 1L;

    UserDto userDto = userService.toDto(userRepository.findById(id).get());
    List<Task> tasks = taskRepository.findAll();

    Set<TaskDto> taskDto = tasks.stream().map(task -> taskService.toDto(task)).collect(Collectors.toSet());
    userDto.setTasks(taskDto);

    Assertions.assertEquals("test", userDto.getName());
    Assertions.assertEquals(UserRole.ROLE_USER, userDto.getRole());
    Assertions.assertEquals(1, userDto.getId());
    Assertions.assertEquals(2, userDto.getTasks().size());

  }

}
