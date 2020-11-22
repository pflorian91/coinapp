package coinapp.domain.service;

import static coinapp.domain.model.TransactionStatus.ACCEPTED;
import static coinapp.domain.model.TransactionStatus.CREATED;
import static coinapp.domain.model.TransactionStatus.REJECTED;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import coinapp.domain.model.Wallet;
import coinapp.domain.repository.WalletRepository;

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
    Optional<Wallet> optionalWallet = walletRepository.findById(wallet.getId());

    // create new
    if (optionalWallet.isEmpty()) {
      wallet.setVersion(1);
      walletRepository.save(wallet);

      wallet.getTransaction().setStatus(CREATED);

      return wallet;
    }

    final Wallet existingWallet = optionalWallet.get();

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
    Optional<Wallet> optionalWallet = walletRepository.findById(wallet.getId());

    // no balance
    if (optionalWallet.isEmpty()) {
      wallet.getTransaction().setStatus(REJECTED);
      return wallet;
    }

    final Wallet existingWallet = optionalWallet.get();

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
