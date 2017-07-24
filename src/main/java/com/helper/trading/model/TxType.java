package com.helper.trading.model;

import javax.persistence.*;

@Entity
@Table(name = "tx_types")
public class TxType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    private String name;

    public TxType() {
    }

    public TxType(String name) {
        this.name = name;
    }

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
        if (obj instanceof TxType)
            return this.getName().equals(((TxType) obj).getName());
        else
            return false;
    }
}
