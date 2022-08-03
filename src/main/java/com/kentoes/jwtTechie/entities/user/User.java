package com.kentoes.jwtTechie.entities.user;

import com.kentoes.jwtTechie.entities.BaseEntity;
import com.kentoes.jwtTechie.entities.role.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity {
    private String username;
    private String fullName;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username, String fullName, String password) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
    }

    public void addRole(Role role) {
        roles.add(role);
    }
}
