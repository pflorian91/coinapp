package coinapp.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import coinapp.domain.model.Wallet;
import coinapp.domain.repository.WalletRepository;
import coinapp.domain.service.WalletService;
import coinapp.domain.service.WalletServiceImpl;

/**
 * WalletServiceWrapper
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@Service
public class WalletServiceWrapper implements WalletService {

  private final WalletServiceImpl walletServiceImpl;

  @Autowired
  public WalletServiceWrapper(WalletRepository walletRepository) {
    walletServiceImpl = new WalletServiceImpl(walletRepository);
  }

  @Override
  public Optional<Wallet> findWalletById(String walletId) {
    return walletServiceImpl.findWalletById(walletId);
  }

  @Override
  public Wallet creditWallet(Wallet wallet) {
    return walletServiceImpl.creditWallet(wallet);
  }

  @Override
  public Wallet debitWallet(Wallet wallet) {
    return walletServiceImpl.debitWallet(wallet);
  }
}
