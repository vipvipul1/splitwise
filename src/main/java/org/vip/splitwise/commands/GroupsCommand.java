package org.vip.splitwise.commands;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.vip.splitwise.controllers.GroupController;
import org.vip.splitwise.dtos.UserGroupsResponseDto;
import org.vip.splitwise.models.Group;

import java.util.List;

@Component
public class GroupsCommand implements Command {
    private GroupController groupController;

    @Autowired
    public GroupsCommand(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public boolean matches(String input) {
        String[] params = input.split(" ");
        return params.length == 2 && CommandType.GROUPS.getLabel().equals(params[1]);
    }

    @Override
    public void execute(String input) {
        String[] params = input.split(" ");
        UserGroupsResponseDto responseDto = groupController.getGroupsByUser(params[0]);

        if (responseDto.getResponseCode() == HttpStatus.OK) {
            List<Group> groups = responseDto.getGroups();
            if (groups == null || groups.isEmpty()) {
                System.out.println(responseDto.getResponseMsg() + ". User is not added to any group!");
            } else {
                System.out.println(responseDto.getResponseMsg() + ". List of groups user is added in are:-");
                for (int i = 0; i < groups.size(); i++) {
                    System.out.println((i + 1) + ". " + groups.get(i).getName());
                }
            }
        } else {
            System.out.println(responseDto.getResponseMsg());
        }
    }
}
