package com.ironsheep.coinapp.repository;

import com.ironsheep.coinapp.domainmodel.model.Wallet;
import com.ironsheep.coinapp.domainmodel.repository.WalletRepository;
import com.ironsheep.coinapp.service.WalletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * WalletRepositoryImpl
 * Extra layer to abstract DB knowledge from the domain model
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@Service
@Profile("!mongo")
public class WalletH2RepositoryImpl implements WalletRepository {

    @Autowired
    private WalletH2Repository walletH2Repository;

    @Autowired
    private WalletMapper walletMapper;

    @Override
    public Optional<Wallet> findById(String walletId) {
        return walletH2Repository.findById(walletId).map(walletEntity -> walletMapper.fromEntity(walletEntity));
    }

    @Override
    public void save(Wallet wallet) {
        walletH2Repository.save(walletMapper.toEntity(wallet));
    }
}
