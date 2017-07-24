package com.helper.trading.model;

import com.helper.trading.model.user.User;
import com.helper.trading.util.TxTypeUtil;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "stock_id", nullable = false)
    private Stock stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_pair_id", nullable = false)
    private CurrencyPair currencyPair;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tx_type_id", nullable = false)
    private TxType txType;

    @Column(name = "total", nullable = false)
    private Double total;

    @Transient
    private org.knowm.xchange.currency.CurrencyPair currencyPairInfo;

    @Column(name = "trade_price", nullable = false)
    private Double tradePrice;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @Column(name = "trade_date", columnDefinition = "CURRENT_TIMESTAMP", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tradeDate;

    @Column(name = "notes", length = 250, nullable = true)
    private String notes;

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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public void setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
    }

    public TxType getTxType() {
        return txType;
    }

    public void setTxType(TxType txType) {
        this.txType = txType;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public org.knowm.xchange.currency.CurrencyPair getCurrencyPairInfo() {
        if (currencyPairInfo == null)
            return new org.knowm.xchange.currency.CurrencyPair(getCurrencyPair().getName());

        return currencyPairInfo;
    }

    public void setCurrencyPairInfo(org.knowm.xchange.currency.CurrencyPair currencyPairInfo) {
        this.currencyPairInfo = currencyPairInfo;
    }

    public Double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(Double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
