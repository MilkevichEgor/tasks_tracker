package controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fusiontech.milkevich.tasktracker.constant.TaskStatus;
import com.fusiontech.milkevich.tasktracker.constant.UserRole;
import com.fusiontech.milkevich.tasktracker.controllers.TaskRestController;
import com.fusiontech.milkevich.tasktracker.entity.Task;
import com.fusiontech.milkevich.tasktracker.entity.User;
import com.fusiontech.milkevich.tasktracker.repository.TaskRepository;
import com.fusiontech.milkevich.tasktracker.repository.UserRepository;
import com.fusiontech.milkevich.tasktracker.services.TaskService;
import com.fusiontech.milkevich.tasktracker.services.UserService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = {TaskService.class,
    TaskRestController.class, TaskRepository.class, UserService.class, UserRepository.class})
@ComponentScan(basePackages = {"com.fusiontech.milkevich.tasktracker"})
@TestPropertySource(locations = "classpath:application-test.properties")
@EnableConfigurationProperties
@AutoConfigureMockMvc
class UserRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private TaskRestController taskRestController;

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
  void getByIdTest() throws Exception {
    mockMvc.perform(get("/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status()
            .isOk())
        .andReturn();
  }

  @Test
  @DirtiesContext
  void getAllTest() throws Exception {

    mockMvc.perform(get("/users"))
        .andExpect(status()
            .isOk())
        .andReturn();
  }

  @Test
  @DirtiesContext
  void saveUserTest() throws Exception {
    mockMvc.perform(post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"test3\",\"role\":\"ROLE_USER\"}"))
        .andExpect(status()
            .isOk())
        .andReturn();
  }

  @Test
  @DirtiesContext
  void deleteUserTest() throws Exception {
    mockMvc.perform(delete("/users/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();
  }
}
