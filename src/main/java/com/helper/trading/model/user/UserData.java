package com.helper.trading.model.user;

import javax.persistence.*;

@Entity
@Table(name = "sys_user_data")
public class UserData {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;

    @OneToOne(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @JoinColumn(
            name = "user_id"
    )
    private User user;

    @Column(
            name = "phone",
            length = 20
    )
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
