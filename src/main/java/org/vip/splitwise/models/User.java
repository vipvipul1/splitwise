package org.vip.splitwise.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER")
public class User implements Serializable {
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

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String id;
        private String username;
        private Long phone;
        private String password;
        private List<GroupUser> groups;

        public UserBuilder setId(String id) {
            this.id = id;
            return this;
        }

        public UserBuilder setUsername(String username) {
            this.username = username;
            return this;
        }

        public UserBuilder setPhone(Long phone) {
            this.phone = phone;
            return this;
        }

        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder setGroups(List<GroupUser> groups) {
            this.groups = groups;
            return this;
        }

        public User build() {
            User user = new User();
            user.setId(id);
            user.setUsername(username);
            user.setPhone(phone);
            user.setPassword(password);
            user.setGroups(groups);
            return user;
        }
    }
}
