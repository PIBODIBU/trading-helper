package com.helper.trading.model.security;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "sys_permission")
public class Permission {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

    @Column(
            name = "name"
    )
    private String name;

    @Column(
            name = "description"
    )
    private String description;

    /*@ManyToMany(
            mappedBy = "permissions",
            fetch = FetchType.EAGER,
            cascade = CascadeType.MERGE
    )
    private Set<Role> roles;*/

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /*public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }*/

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
