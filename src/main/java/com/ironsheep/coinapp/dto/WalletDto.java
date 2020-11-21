package com.ironsheep.coinapp.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class WalletDto {

    @JsonIgnore
    private String id;
    private String transactionId;
    private int version;
    private int coins;

    public WalletDto() {}

    public WalletDto(String id, String transactionId, int version, int coins) {
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
