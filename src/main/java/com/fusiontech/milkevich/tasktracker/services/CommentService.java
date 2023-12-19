package com.fusiontech.milkevich.tasktracker.services;

import com.fusiontech.milkevich.tasktracker.dto.CommentDto;
import com.fusiontech.milkevich.tasktracker.entity.Comment;
import com.fusiontech.milkevich.tasktracker.repository.CommentRepository;
import com.fusiontech.milkevich.tasktracker.repository.TaskRepository;
import com.fusiontech.milkevich.tasktracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Comment service.
 */
@Service
public class CommentService extends AbstractService<Comment, CommentDto, CommentRepository> {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TaskRepository taskRepository;

  @Override
  protected String getMapName() {
    return "CommentMap";
  }

  /**
   * Convert entity to dto.
   *
   * @param comment - entity comment
   * @return - comment dto
   */
  public CommentDto toDto(Comment comment) {
    return new CommentDto(
        comment.getId(),
        comment.getText(),
        comment.getUser().getId(),
        comment.getTask().getId()
    );
  }

  /**
   * Convert dto to entity.
   *
   * @param commentDto - dto comment
   * @return - comment
   */
  public Comment toEntity(CommentDto commentDto) {
    return new Comment(
        commentDto.getId(),
        commentDto.getText(),
        userRepository.findById(commentDto.getUserId()).get(),
        taskRepository.findById(commentDto.getTaskId()).get()
    );
  }
}
