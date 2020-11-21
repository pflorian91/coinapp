package com.ironsheep.coinapp.domainmodel.model;

/**
 * Wallet
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
public class Wallet {

    private String id;
    private Transaction transaction;
    private int version;
    private int coins;

    // FIXME make private, remove setters, make fields final
    public Wallet(String id, Transaction transaction, int version, int coins) {
        this.id = id;
        this.transaction = transaction;
        this.version = version;
        this.coins = coins;
    }

    public String getId() {
        return id;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public int getVersion() {
        return version;
    }

    public int getCoins() {
        return coins;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Wallet wallet = (Wallet) o;

        if (version != wallet.version) {
            return false;
        }
        if (coins != wallet.coins) {
            return false;
        }
        if (!id.equals(wallet.id)) {
            return false;
        }
        return transaction.equals(wallet.transaction);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + transaction.hashCode();
        result = 31 * result + version;
        result = 31 * result + coins;
        return result;
    }

    @Override
    public String toString() {
        return "Wallet{" +
            "id='" + id + '\'' +
            ", transaction=" + transaction +
            ", version=" + version +
            ", coins=" + coins +
            '}';
    }

    public static class WalletBuilder {

        private String id;
        private Transaction transaction;
        private int version;
        private int coins;

        public static WalletBuilder aWallet() {
            return new Wallet.WalletBuilder();
        }

        public Wallet build() {
            return new Wallet(id, transaction, version, coins);
        }

        public WalletBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public WalletBuilder withTransaction(Transaction transaction) {
            this.transaction = transaction;
            return this;
        }

        public WalletBuilder withVersion(int version) {
            this.version = version;
            return this;
        }

        public WalletBuilder withCoins(int coins) {
            this.coins = coins;
            return this;
        }
    }
}
