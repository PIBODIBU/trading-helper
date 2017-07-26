package com.helper.trading.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "currency_pairs")
public class CurrencyPair {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    private Long id;
    @Column(
            name = "name",
            length = 100,
            nullable = false
    )
    private String name;


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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CurrencyPair)
            return this.getId().equals(((CurrencyPair) obj).getId());

        return false;
    }
}
