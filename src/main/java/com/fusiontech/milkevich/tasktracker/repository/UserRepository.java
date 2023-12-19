package com.fusiontech.milkevich.tasktracker.repository;

import com.fusiontech.milkevich.tasktracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
