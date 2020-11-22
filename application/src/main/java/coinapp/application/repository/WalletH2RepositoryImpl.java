package coinapp.application.repository;

import coinapp.application.service.WalletMapper;
import coinapp.domain.model.Wallet;
import coinapp.domain.repository.WalletRepository;

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
