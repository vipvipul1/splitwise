package org.vip.splitwise.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER")
public class User extends BaseModel {
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PHONE", unique = true)
    private Long phone;

    @Column(name = "PASSWORD")
    private String password;

    @JsonIgnoreProperties({"createdBy", "members"})
    @ManyToMany
    @JoinTable(name = "GROUP_USER",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")})
    private List<Group> groups;
}
