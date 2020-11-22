package coinapp.application.repository;

import coinapp.application.service.WalletMapper;
import coinapp.domain.model.Wallet;
import coinapp.domain.repository.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

/**
 * WalletMongoRepositoryImpl
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@Profile("mongo")
public class WalletMongoRepositoryImpl implements WalletRepository {

  @Autowired
  private WalletMongoRepository walletMongoRepository;

  @Autowired
  private WalletMapper walletMapper;

  @Override
  public Optional<Wallet> findById(String walletId) {
    return walletMongoRepository.findById(walletId).map(walletEntity -> walletMapper.fromEntity(walletEntity));
  }

  @Override
  public void save(Wallet wallet) {
    walletMongoRepository.save(walletMapper.toEntity(wallet));
  }
}
