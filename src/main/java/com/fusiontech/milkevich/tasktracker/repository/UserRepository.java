package com.fusiontech.milkevich.tasktracker.repository;

import com.fusiontech.milkevich.tasktracker.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  /**
   * Find by name.
   *
   * @param name - name
   * @return - user
   */
  Optional<User> findByName(String name);

  boolean existsByName(String name);
}
