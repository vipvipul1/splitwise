package org.vip.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.vip.splitwise.controllers.GroupController;
import org.vip.splitwise.dtos.GroupRequestDto;
import org.vip.splitwise.dtos.GroupResponseDto;
import org.vip.splitwise.models.Group;
import org.vip.splitwise.models.User;

@Component
public class AddGroupCommand implements Command {
    /*
        Expected Input:     u1 AddGroup Roommates (u1 is creating a group titled "Roommates")
        Expected Output:    SUCCESS. Group Id: g1
    */
    private GroupController groupController;

    @Autowired
    public AddGroupCommand(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public boolean matches(String input) {
        String[] params = input.split(" ");
        return params.length >= 3 && CommandType.ADD_GROUP.getLabel().equals(params[1]);
    }

    @Override
    public void execute(String input) {
        String[] params = input.split(" ");
        GroupRequestDto requestDto = new GroupRequestDto();

        User user = new User();
        user.setId(params[0]);

        Group group = new Group();
        group.setCreatedBy(user);
        group.setName(input.split(" AddGroup ")[1]);

        requestDto.setGroup(group);
        GroupResponseDto responseDto = groupController.addGroup(requestDto);
        if (responseDto.getResponseCode() == HttpStatus.OK) {
            System.out.println(responseDto.getResponseMsg() + ". Group Id: " + responseDto.getGroup().getId());
        } else {
            System.out.println(responseDto.getResponseMsg());
        }
    }
}
