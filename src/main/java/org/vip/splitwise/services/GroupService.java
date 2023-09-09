package org.vip.splitwise.services;

import org.vip.splitwise.models.Group;
import org.vip.splitwise.models.User;

public interface GroupService {

    Group addGroup(String name, User createdBy);

    Group addGroupMember(Group group, User addedUser, User addedByUser);
}
