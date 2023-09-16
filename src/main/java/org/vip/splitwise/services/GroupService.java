package org.vip.splitwise.services;

import org.vip.splitwise.models.Group;
import org.vip.splitwise.models.User;

import java.util.List;

public interface GroupService {

    Group addGroup(String name, User createdBy);

    Group addGroupMember(Group group, User addedUser, User addedByUser);

    List<Group> getGroupsByUser(String userId);
}
