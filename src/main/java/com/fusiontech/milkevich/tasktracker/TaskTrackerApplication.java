package com.fusiontech.milkevich.tasktracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Task tracker application.
 */
@SpringBootApplication
@EnableCaching
public class TaskTrackerApplication {

  /**
   * Main method.
   *
   * @param args - args
   */
  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(TaskTrackerApplication.class);
//    application.setAdditionalProfiles("docker");
    application.run(args);
  }
}