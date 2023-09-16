package org.vip.splitwise.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "GROUP")
public class Group implements Serializable {
    @Id
    @GeneratedValue(generator = "group-generator")
    @GenericGenerator(name = "group-generator",
            parameters = @Parameter(name = "prefix", value = "g"),
            type = SplitwiseIdGenerator.class)
    @Column(name = "ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @JsonIgnoreProperties({"group", "addedBy"})
    @OneToMany(mappedBy = "group")
    private List<GroupUser> users;

    @OneToMany
    @JoinTable(name = "GROUP_EXPENSE",
            joinColumns = {@JoinColumn(name = "GROUP_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EXPENSE_ID", unique = true)})
    private List<Expense> expenses;

    public static GroupBuilder builder() {
        return new GroupBuilder();
    }

    public static class GroupBuilder {
        private String id;
        private String name;
        private LocalDateTime createdOn;
        private User createdBy;
        private List<GroupUser> users;
        private List<Expense> expenses;

        public GroupBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public GroupBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public GroupBuilder setCreatedOn(LocalDateTime createdOn) {
            this.createdOn = createdOn;
            return this;
        }

        public GroupBuilder setCreatedBy(User createdBy) {
            this.createdBy = createdBy;
            return this;
        }

        public GroupBuilder setUsers(List<GroupUser> users) {
            this.users = users;
            return this;
        }

        public GroupBuilder setExpenses(List<Expense> expenses) {
            this.expenses = expenses;
            return this;
        }

        public Group build() {
            Group group = new Group();
            group.setId(id);
            group.setName(name);
            group.setCreatedBy(createdBy);
            group.setCreatedOn(createdOn);
            group.setUsers(users);
            group.setExpenses(expenses);
            return group;
        }
    }
}
