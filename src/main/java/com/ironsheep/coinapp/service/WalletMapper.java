package com.ironsheep.coinapp.service;

import com.ironsheep.coinapp.dto.WalletDto;
import com.ironsheep.coinapp.entity.WalletEntity;
import com.ironsheep.coinapp.domainmodel.model.Transaction;
import com.ironsheep.coinapp.domainmodel.model.Wallet;
import org.springframework.stereotype.Service;

/**
 * WalletMapper
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@Service
public class WalletMapper {

    public Wallet fromDto(WalletDto dto) {
        // FIXME use builder
        Transaction transaction = new Transaction(null, dto.getTransactionId());
        return new Wallet(dto.getId(), transaction, dto.getVersion(), dto.getCoins());
    }

    public WalletDto toDto(Wallet wallet) {
        // FIXME use builder
        return new WalletDto(wallet.getId(), wallet.getTransaction().getId(), wallet.getVersion(), wallet.getCoins());
    }

    public Wallet fromEntity(WalletEntity entity) {
        Transaction transaction = new Transaction(null, entity.getTransactionId());
        return new Wallet(entity.getId(), transaction, entity.getVersion(), entity.getCoins());
    }

    public WalletEntity toEntity(Wallet wallet) {
        return new WalletEntity(wallet.getId(), wallet.getTransaction().getId(), wallet.getVersion(), wallet.getCoins());
    }
}
