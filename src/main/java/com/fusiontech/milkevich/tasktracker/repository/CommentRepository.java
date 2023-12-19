package com.fusiontech.milkevich.tasktracker.repository;

import com.fusiontech.milkevich.tasktracker.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Comment repository.
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
