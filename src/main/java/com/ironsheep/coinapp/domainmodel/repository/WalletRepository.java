package com.ironsheep.coinapp.domainmodel.repository;

import com.ironsheep.coinapp.domainmodel.model.Wallet;

import java.util.Optional;

/**
 * WalletRepository
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
public interface WalletRepository {

    Optional<Wallet> findById(String walletId);

    void save(Wallet wallet);

}
