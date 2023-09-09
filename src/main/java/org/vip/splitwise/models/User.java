package org.vip.splitwise.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(generator = "user-generator")
    @GenericGenerator(name = "user-generator",
            parameters = @Parameter(name = "prefix", value = "u"),
            type = SplitwiseIdGenerator.class)
    @Column(name = "ID")
    private String id;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PHONE", unique = true)
    private Long phone;

    @Column(name = "PASSWORD")
    private String password;

    @JsonIgnoreProperties({"user", "addedBy"})
    @OneToMany(mappedBy = "user")
    private List<GroupUser> groups;
}
