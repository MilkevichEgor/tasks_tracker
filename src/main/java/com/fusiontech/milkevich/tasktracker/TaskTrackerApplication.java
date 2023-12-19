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

  public static void main(String[] args) {
    SpringApplication.run(TaskTrackerApplication.class, args);
  }
}