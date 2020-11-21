package com.ironsheep.coinapp.domainmodel.service;

import com.ironsheep.coinapp.domainmodel.model.Wallet;
import com.ironsheep.coinapp.domainmodel.repository.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ironsheep.coinapp.domainmodel.model.TransactionStatus.*;

/**
 * WalletService
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Optional<Wallet> findWalletById(String walletId) {
        return walletRepository.findById(walletId);
    }

    public Wallet creditWallet(Wallet wallet) {
        Wallet existingWallet = walletRepository.findById(wallet.getId()).orElse(null);

        // create new
        if (null == existingWallet) {
            wallet.setVersion(1);
            walletRepository.save(wallet);

            wallet.getTransaction().setStatus(CREATED);

            return wallet;
        }

        // same transaction, do nothing
        if (wallet.getTransaction().getId().equals(existingWallet.getTransaction().getId())) {
            existingWallet.getTransaction().setStatus(ACCEPTED);
            return existingWallet;
        }

        // credit existing balance
        existingWallet.setVersion(existingWallet.getVersion() + 1);
        existingWallet.setCoins(existingWallet.getCoins() + wallet.getCoins());
        existingWallet.getTransaction().setId(wallet.getTransaction().getId());

        walletRepository.save(existingWallet);

        existingWallet.getTransaction().setStatus(CREATED);

        return existingWallet;
    }

    public Wallet debitWallet(Wallet wallet) {
        Wallet existingWallet = walletRepository.findById(wallet.getId()).orElse(null);

        // no balance
        if (null == existingWallet) {
            wallet.getTransaction().setStatus(REJECTED);
            return wallet;
        }

        // same transaction, do nothing
        if (wallet.getTransaction().getId().equals(existingWallet.getTransaction().getId())) {
            existingWallet.getTransaction().setStatus(ACCEPTED);
            return existingWallet;
        }

        // less coins than desired debit
        if (wallet.getCoins() > existingWallet.getCoins()) {
            existingWallet.getTransaction().setStatus(REJECTED);
            return existingWallet;
        }

        // more coins, can debit
        existingWallet.setVersion(existingWallet.getVersion() + 1);
        existingWallet.setCoins(existingWallet.getCoins() - wallet.getCoins());
        existingWallet.getTransaction().setId(wallet.getTransaction().getId());

        walletRepository.save(existingWallet);

        existingWallet.getTransaction().setStatus(CREATED);

        return existingWallet;
    }
}
