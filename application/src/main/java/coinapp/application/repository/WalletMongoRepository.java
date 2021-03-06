package coinapp.application.repository;

import coinapp.application.entity.WalletEntity;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * WalletMongoRepository
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@Profile("mongo")
public interface WalletMongoRepository extends MongoRepository<WalletEntity, String> {

}
