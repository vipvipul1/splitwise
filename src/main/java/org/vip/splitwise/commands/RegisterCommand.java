package org.vip.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.vip.splitwise.controllers.UserController;
import org.vip.splitwise.dtos.UserRequestDto;
import org.vip.splitwise.dtos.UserResponseDto;

import java.util.Arrays;
import java.util.List;

@Component
public class RegisterCommand implements Command {
    /*
        Expected Input:     Register vipvipul 7778889990 pass (user is registering with username "vipvipul", phone "7778889990", password "pass")
        Expected Output:    SUCCESS. User Id: u1
    */
    private UserController userController;

    @Autowired
    public RegisterCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean matches(String input) {
        String[] params = input.split(" ");
        return params.length == 4 && CommandType.REGISTER.getLabel().equals(params[0]);
    }

    @Override
    public void execute(String input) {
        String[] params = input.split(" ");
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setUsername(params[1]);
        requestDto.setPhone(Long.parseLong(params[2]));
        requestDto.setPassword(params[3]);
        UserResponseDto responseDto = userController.registerUser(requestDto);
        if (responseDto.getResponseCode() == HttpStatus.OK)
            System.out.println(responseDto.getResponseMsg() + ". User Id: " + responseDto.getUserId());
        else
            System.out.println(responseDto.getResponseMsg());
    }
}
