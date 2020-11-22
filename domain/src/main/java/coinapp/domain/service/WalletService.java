package coinapp.domain.service;

import java.util.Optional;

import coinapp.domain.model.Wallet;

/**
 * WalletService
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
public interface WalletService {

  Optional<Wallet> findWalletById(String walletId);

  Wallet creditWallet(Wallet wallet);

  Wallet debitWallet(Wallet wallet);

}
