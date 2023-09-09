package org.vip.splitwise.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "GROUP")
public class Group {
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
}
