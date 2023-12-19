package services;

import com.fusiontech.milkevich.tasktracker.constant.TaskStatus;
import com.fusiontech.milkevich.tasktracker.constant.UserRole;
import com.fusiontech.milkevich.tasktracker.dto.TaskDto;
import com.fusiontech.milkevich.tasktracker.entity.Task;
import com.fusiontech.milkevich.tasktracker.entity.User;
import com.fusiontech.milkevich.tasktracker.repository.TaskRepository;
import com.fusiontech.milkevich.tasktracker.repository.UserRepository;
import com.fusiontech.milkevich.tasktracker.services.TaskService;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {TaskRepository.class, TaskService.class})
@ComponentScan(basePackages = {"com.fusiontech.milkevich.tasktracker"})
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableConfigurationProperties
@ActiveProfiles("test")
class TaskServiceTest {

  @Autowired
  private TaskService taskService;

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private UserRepository userRepository;

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
    user.setName(name);
    user.setRole(UserRole.ROLE_USER);
    user.setTasks(List.of());
    return user;
  }

  public Task createTask(String name) {
    Task task = new Task();
    task.setTitle("test title");
    task.setDescription("test description");
    task.setStatus(TaskStatus.ACTIVE);
    return task;
  }

  @Test
  @DirtiesContext
  void getTaskTest() {
    Long id = 1L;

    Task task = taskRepository.findById(id).get();

    Assertions.assertEquals("test title", task.getTitle());
    Assertions.assertEquals("test description", task.getDescription());
    Assertions.assertEquals(TaskStatus.ACTIVE, task.getStatus());
  }

  @Test
  @DirtiesContext
  void saveTaskTest() {
    Task task = new Task();
    task.setTitle("test title2");
    task.setDescription("test description2");
    task.setStatus(TaskStatus.ACTIVE);
    taskRepository.save(task);

    Assertions.assertEquals(3, taskRepository.findAll().size());
  }

  @Test
  @DirtiesContext
  void deleteTaskTest() {
    Long id = 1L;
    taskRepository.deleteById(id);
    Assertions.assertEquals(1, taskRepository.findAll().size());
  }

  @Test
  @DirtiesContext
  void changeStatusTest() {
    Long id = 1L;
    Task task = taskRepository.findById(id).get();
    task.setStatus(TaskStatus.COMPLETED);
    taskRepository.save(task);

    Assertions.assertEquals(TaskStatus.COMPLETED, task.getStatus());
  }

  @Test
  @DirtiesContext
  void toDtoTest() {
    Long id = 1L;

    User user = userRepository.findById(id).get();
    Task task = taskRepository.findById(id).get();
    TaskDto taskDto = taskService.toDto(task);

    taskDto.setUserId(user.getId());

    Assertions.assertEquals("test title", taskDto.getTitle());
    Assertions.assertEquals("test description", taskDto.getDescription());
    Assertions.assertEquals(TaskStatus.ACTIVE, taskDto.getStatus());
    Assertions.assertEquals(1, taskDto.getUserId());
  }
}
