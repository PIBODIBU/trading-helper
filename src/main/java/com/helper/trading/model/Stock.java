package com.helper.trading.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "name_java", nullable = false, length = 100)
    private String nameJava;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "stock")
    private Set<Transaction> transactions;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinTable(name = "rel_stock_currency",
            joinColumns = @JoinColumn(
                    name = "stock_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "currency_pair_id",
                    referencedColumnName = "id"
            )
    )
    private Set<CurrencyPair> currencyPairs;

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

    public String getNameJava() {
        return nameJava;
    }

    public void setNameJava(String nameJava) {
        this.nameJava = nameJava;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Set<CurrencyPair> getCurrencyPairs() {
        return currencyPairs;
    }

    public void setCurrencyPairs(Set<CurrencyPair> currencyPairs) {
        this.currencyPairs = currencyPairs;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Stock)
            return this.getId().equals(((Stock) obj).getId());

        return false;
    }
}
