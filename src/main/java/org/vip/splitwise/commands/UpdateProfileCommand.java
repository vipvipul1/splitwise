package org.vip.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vip.splitwise.controllers.UserController;

@Component
public class UpdateProfileCommand implements Command {
    // Expected Input:  vipvipul UpdateProfile passcode (user with username "vipvipul" is updating his password to "passcode"
    // Expected Output: SUCCESS
    private UserController userController;

    @Autowired
    public UpdateProfileCommand(UserController userController) {
        this.userController = userController;
    }

    @Override
    public boolean matches(String input) {
        String[] params = input.split(" ");
        return params.length == 3 && CommandType.UPDATE_PROFILE.getLabel().equals(params[0]);
    }

    @Override
    public void execute(String input) {
        System.out.println("Inside UpdateProfileCommand");
    }
}
