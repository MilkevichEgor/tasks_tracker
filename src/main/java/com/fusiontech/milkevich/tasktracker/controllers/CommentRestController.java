package com.fusiontech.milkevich.tasktracker.controllers;

import com.fusiontech.milkevich.tasktracker.dto.CommentDto;
import com.fusiontech.milkevich.tasktracker.entity.Comment;
import com.fusiontech.milkevich.tasktracker.repository.CommentRepository;
import com.fusiontech.milkevich.tasktracker.services.CommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Comment rest controller.
 */
@RestController
@RequestMapping("/comments")
public class CommentRestController
    extends AbstractRestController<CommentService, CommentRepository, Comment, CommentDto> {
}
