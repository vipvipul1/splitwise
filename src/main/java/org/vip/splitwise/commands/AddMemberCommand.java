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
public class AddMemberCommand implements Command {
    /*
        Expected Input:     u1 AddMember g1 u2 (u1 is adding u2 as a member of the group "Roommates" (which has id g1))
        Expected Output:    SUCCESS. u2 added in group: Roommates
    */
    private GroupController groupController;

    @Autowired
    public AddMemberCommand(GroupController groupController) {
        this.groupController = groupController;
    }

    @Override
    public boolean matches(String input) {
        String[] params = input.split(" ");
        return params.length == 4 && CommandType.ADD_MEMBER.getLabel().equals(params[1]);
    }

    @Override
    public void execute(String input) {
        String[] params = input.split(" ");
        GroupRequestDto requestDto = new GroupRequestDto();

        User addedByUser = new User();
        addedByUser.setId(params[0]);

        Group group = new Group();
        group.setId(params[2]);

        User addedUser = new User();
        addedUser.setId(params[3]);

        requestDto.setGroup(group);
        requestDto.setAddedUser(addedUser);
        requestDto.setAddedByUser(addedByUser);
        GroupResponseDto responseDto = groupController.addGroupMember(requestDto);
        if (responseDto.getResponseCode() == HttpStatus.OK) {
            System.out.println(responseDto.getResponseMsg() + ". User " + addedUser.getId()
                    + " added in group: " + responseDto.getGroup().getName());
        } else {
            System.out.println(responseDto.getResponseMsg());
        }
    }
}
