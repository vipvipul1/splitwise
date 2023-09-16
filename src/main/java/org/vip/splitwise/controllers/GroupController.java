package org.vip.splitwise.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.vip.splitwise.dtos.GroupRequestDto;
import org.vip.splitwise.dtos.GroupResponseDto;
import org.vip.splitwise.dtos.UserGroupsResponseDto;
import org.vip.splitwise.models.Group;
import org.vip.splitwise.models.User;
import org.vip.splitwise.services.GroupService;

import java.util.List;

@Controller
public class GroupController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);

    private GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    public GroupResponseDto addGroup(GroupRequestDto requestDto) {
        GroupResponseDto responseDto = new GroupResponseDto();
        try {
            Group group = groupService.addGroup(requestDto.getGroup().getName(), requestDto.getGroup().getCreatedBy());
            responseDto.setGroup(group);
            responseDto.setResponseCode(HttpStatus.OK);
            responseDto.setResponseMsg("SUCCESS");
        } catch (Exception e) {
            LOGGER.error("Error in GroupController -> addGroup() : " + e.getMessage());
            responseDto.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDto.setResponseMsg("FAILURE. Reason: " + e.getMessage());
        }
        return responseDto;
    }

    public GroupResponseDto addGroupMember(GroupRequestDto requestDto) {
        GroupResponseDto responseDto = new GroupResponseDto();
        try {
            Group group = groupService.addGroupMember(requestDto.getGroup(), requestDto.getAddedUser(), requestDto.getAddedByUser());
            responseDto.setGroup(group);
            responseDto.setResponseCode(HttpStatus.OK);
            responseDto.setResponseMsg("SUCCESS");
        } catch (Exception e) {
            LOGGER.error("Error in GroupController -> addGroupMember() : " + e.getMessage());
            responseDto.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDto.setResponseMsg("FAILURE. Reason: " + e.getMessage());
        }
        return responseDto;
    }

    public UserGroupsResponseDto getGroupsByUser(String userId) {
        UserGroupsResponseDto responseDto = new UserGroupsResponseDto();
        try {
            List<Group> groups = groupService.getGroupsByUser(userId);
            responseDto.setGroups(groups);
            responseDto.setResponseCode(HttpStatus.OK);
            responseDto.setResponseMsg("SUCCESS");
        } catch (Exception e) {
            LOGGER.error("Error in GroupController -> getGroupsByUser() : " + e.getMessage());
            responseDto.setResponseCode(HttpStatus.INTERNAL_SERVER_ERROR);
            responseDto.setResponseMsg("FAILURE. Reason: " + e.getMessage());
        }
        return responseDto;
    }
}
