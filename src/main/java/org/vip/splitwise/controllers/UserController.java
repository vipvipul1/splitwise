package org.vip.splitwise.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.vip.splitwise.dtos.UserRequestDto;
import org.vip.splitwise.dtos.UserResponseDto;
import org.vip.splitwise.models.User;
import org.vip.splitwise.services.UserService;

@Controller
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserResponseDto registerUser(UserRequestDto requestDto) {
        UserResponseDto responseDto = new UserResponseDto();
        try {
            User user = userService.registerUser(requestDto);
            responseDto.setUserId(user.getId());
            responseDto.setUsername(user.getUsername());
            responseDto.setPhone(user.getPhone());
            responseDto.setResponseCode(HttpStatus.OK);
            responseDto.setResponseMsg("SUCCESS");
        } catch (Exception e) {
            LOGGER.error("Error in UserController -> registerUser() : " + e.getMessage());
            responseDto.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDto.setResponseMsg("FAILURE. Reason: " + e.getMessage());
        }
        return responseDto;
    }
}
