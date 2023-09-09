package org.vip.splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class GroupUserId implements Serializable {

    @Column(name = "GROUP_ID")
    private String groupId;

    @Column(name = "USER_ID")
    private String userId;

    // This is required for JPA to uniquely identify the composite primary key of (groupId, userId)
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GroupUserId that = (GroupUserId) o;
        return Objects.equals(groupId, that.groupId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, userId);
    }
}
