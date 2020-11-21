package com.ironsheep.coinapp.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Transaction
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@JsonAutoDetect
@Entity
@Table
public class WalletEntity {

    @Id
    @Column
    private String id;

    @Column
    private String transactionId;

    @Column
    // FIXME what is with the version?? increments with each transaction or??
    private int version;

    @Column
    private int coins;

    public WalletEntity() {
    }

    public WalletEntity(String id, String transactionId, int version, int coins) {
        this.id = id;
        this.transactionId = transactionId;
        this.version = version;
        this.coins = coins;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
