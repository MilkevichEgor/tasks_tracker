import com.fusiontech.milkevich.tasktracker.TaskTrackerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = TaskTrackerApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class TasksApplicationTest {

  @Test
  public void test() {

  }
}
