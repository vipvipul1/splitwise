package org.vip.splitwise.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "GROUP_USER")
public class GroupUser implements Serializable {
    // This is composite primary key. Instead of having a simple @ManyToMany relationship between Group & User
    // with a mapping table GROUP_USER(without an explicit class) we have created this mapping table class
    // to store an extra attribute addedBy. This a way to store extra attributes in a @ManyToMany mapping table.
    @EmbeddedId
    private GroupUserId groupUserId = new GroupUserId();

    @JsonIgnoreProperties({"users", "expenses", "createdBy"})
    @ManyToOne
    @MapsId("groupId")  // this belongs to groupId in GroupUserId
    @JoinColumn(name = "GROUP_ID")
    private Group group;

    @JsonIgnoreProperties({"groups"})
    @ManyToOne
    @MapsId("userId")   // this belongs to userId in GroupUserId
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ADDED_BY_USER_ID")
    private User addedBy;

    public static GroupUserBuilder builder() {
        return new GroupUserBuilder();
    }

    public static class GroupUserBuilder {
        private Group group;
        private User user;
        private User addedBy;

        public GroupUserBuilder setGroup(Group group) {
            this.group = group;
            return this;
        }

        public GroupUserBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public GroupUserBuilder setAddedBy(User addedBy) {
            this.addedBy = addedBy;
            return this;
        }

        public GroupUser build() {
            GroupUser groupUser = new GroupUser();
            groupUser.setGroup(group);
            groupUser.setUser(user);
            groupUser.setAddedBy(addedBy);
            return groupUser;
        }
    }
}
