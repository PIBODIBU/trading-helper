package com.helper.trading.model.security;

import com.helper.trading.model.user.User;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sys_role")
public class Role {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

    @Column(
            name = "role_name"
    )
    private String roleName;

    @Column(
            name = "description"
    )
    private String description;

    /*@ManyToMany(
            mappedBy = "roles",
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    private Set<User> users;*/

    @ManyToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name = "sys_rel_role_permission",
            joinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id",
                    referencedColumnName = "id"
            )
    )
    private Set<Permission> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /*public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }*/

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
