package com.fusiontech.milkevich.tasktracker.controllers;

import com.fusiontech.milkevich.tasktracker.dto.UserDto;
import com.fusiontech.milkevich.tasktracker.entity.User;
import com.fusiontech.milkevich.tasktracker.repository.UserRepository;
import com.fusiontech.milkevich.tasktracker.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User rest controller.
 */
@RestController
@RequestMapping("/users")
public class UserRestController
    extends AbstractRestController<UserService, UserRepository, User, UserDto> {

}
