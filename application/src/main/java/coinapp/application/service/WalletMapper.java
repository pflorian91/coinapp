package coinapp.application.service;

import coinapp.application.dto.WalletDto;
import coinapp.application.entity.WalletEntity;
import coinapp.domain.model.Transaction;
import coinapp.domain.model.Wallet;

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
