package org.vip.splitwise.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "GROUP")
public class Group extends BaseModel {
    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @OneToOne
    @JoinColumn(name = "CREATED_BY")
    private User createdBy;

    @JsonIgnoreProperties({"groups"})
    @ManyToMany
    @JoinTable(name = "GROUP_USER",
            joinColumns = {@JoinColumn(name = "GROUP_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    private List<User> members;

    @JsonIgnoreProperties({"group"})
    @OneToMany
    @JoinTable(name = "GROUP_EXPENSE",
            joinColumns = {@JoinColumn(name = "GROUP_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EXPENSE_ID")})
    private List<Expense> expenses;
}
