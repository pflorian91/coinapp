package coinapp.application.repository;

import coinapp.application.entity.WalletEntity;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

/**
 * WalletRepository
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@Profile("!mongo")
public interface WalletH2Repository extends CrudRepository<WalletEntity, String> {

}
