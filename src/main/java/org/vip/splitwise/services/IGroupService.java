package org.vip.splitwise.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vip.splitwise.models.Group;
import org.vip.splitwise.models.GroupUser;
import org.vip.splitwise.models.User;
import org.vip.splitwise.repositories.GroupRepository;
import org.vip.splitwise.repositories.GroupUserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IGroupService implements GroupService {
    private static final Logger LOGGER = LoggerFactory.getLogger(IGroupService.class);

    private GroupRepository groupRepository;
    private GroupUserRepository groupUserRepository;

    @Autowired
    public IGroupService(GroupRepository groupRepository, GroupUserRepository groupUserRepository) {
        this.groupRepository = groupRepository;
        this.groupUserRepository = groupUserRepository;
    }

    @Override
    @Transactional
    public Group addGroup(String name, User createdBy) {
        Group savedGroup;
        try {
            Group group = new Group();
            group.setName(name);
            group.setCreatedBy(createdBy);
            group.setCreatedOn(LocalDateTime.now());
            savedGroup = groupRepository.save(group);

            addGroupMember(savedGroup, createdBy, createdBy);
        } catch (Exception e) {
            LOGGER.error("Error in IGroupService -> addGroup() : " + e.getMessage());
            throw e;
        }
        return savedGroup;
    }

    @Override
    @Transactional
    public Group addGroupMember(Group group, User addedUser, User addedByUser) {
        Group groupDb;
        try {
            GroupUser groupUser = GroupUser.builder().setGroup(group).setUser(addedUser).setAddedBy(addedByUser).build();
            groupDb = groupUserRepository.save(groupUser).getGroup();
        } catch (Exception e) {
            LOGGER.error("Error in IGroupService -> addGroupMember() : " + e.getMessage());
            throw e;
        }
        return groupDb;
    }

    @Override
    @Transactional
    public List<Group> getGroupsByUser(String userId) {
        List<Group> groups;
        try {
            List<GroupUser> groupUsers = groupUserRepository.getGroupsByUserId(userId);
            groups = groupUsers.stream().map(GroupUser::getGroup).toList();
        } catch (Exception e) {
            LOGGER.error("Error in IGroupService -> getGroupsByUser() : " + e.getMessage());
            throw e;
        }
        return groups;
    }
}
