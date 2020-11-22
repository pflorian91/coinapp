package coinapp.application.service;

import static coinapp.domain.model.Transaction.TransactionBuilder.aTransaction;
import static coinapp.domain.model.Wallet.WalletBuilder.aWallet;

import org.springframework.stereotype.Service;

import coinapp.application.dto.WalletDto;
import coinapp.application.entity.WalletEntity;
import coinapp.domain.model.Transaction;
import coinapp.domain.model.Wallet;

/**
 * WalletMapper
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@Service
public class WalletMapper {

  public Wallet fromDto(WalletDto dto) {
    return aWallet()
        .withId(dto.getId())
        .withTransaction(
            aTransaction()
                .withStatus(null)
                .withId(dto.getTransactionId())
                .build()
        )
        .withVersion(dto.getVersion())
        .withCoins(dto.getCoins())
        .build();
  }

  public WalletDto toDto(Wallet wallet) {
    return new WalletDto(wallet.getId(), wallet.getTransaction().getId(), wallet.getVersion(), wallet.getCoins());
  }

  public Wallet fromEntity(WalletEntity entity) {
      return aWallet()
          .withId(entity.getId())
          .withTransaction(
              aTransaction()
                  .withStatus(null)
                  .withId(entity.getTransactionId())
                  .build()
          )
          .withVersion(entity.getVersion())
          .withCoins(entity.getCoins())
          .build();
  }

  public WalletEntity toEntity(Wallet wallet) {
    return new WalletEntity(wallet.getId(), wallet.getTransaction().getId(), wallet.getVersion(), wallet.getCoins());
  }
}
