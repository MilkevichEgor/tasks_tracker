package com.fusiontech.milkevich.tasktracker.repository;

import com.fusiontech.milkevich.tasktracker.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
