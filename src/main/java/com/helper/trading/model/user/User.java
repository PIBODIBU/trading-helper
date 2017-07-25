package com.helper.trading.model.user;

import com.helper.trading.model.security.Role;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sys_user")
@Inheritance
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

    @Column(
            name = "name_first"
    )
    private String firstName;

    @Column(
            name = "name_last"
    )
    private String lastName;

    @Column(
            name = "name_middle"
    )
    private String middleName;

    @Column(
            name = "email"
    )
    private String email;

    @Column(
            name = "username"
    )
    private String username;

    @Column(
            name = "password"
    )
    private String password;

    @Column(
            name = "is_enabled"
    )
    private Boolean isEnabled;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinTable(name = "sys_rel_user_role",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
