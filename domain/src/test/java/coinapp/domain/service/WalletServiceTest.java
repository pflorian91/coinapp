package coinapp.domain.service;

import static coinapp.domain.model.Transaction.TransactionBuilder.aTransaction;
import static coinapp.domain.model.TransactionStatus.ACCEPTED;
import static coinapp.domain.model.TransactionStatus.CREATED;
import static coinapp.domain.model.TransactionStatus.REJECTED;
import static coinapp.domain.model.Wallet.WalletBuilder.aWallet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static java.util.Optional.of;

import coinapp.domain.model.Wallet;
import coinapp.domain.repository.WalletRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

/**
 * WalletServiceTest
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@ExtendWith(MockitoExtension.class)
public class WalletServiceTest {

  private final String transactionId = "1a123-aca12-as123";

  private final String walletId = "a1-b2-3ce";

  @InjectMocks
  private WalletService walletService;

  @Mock
  private WalletRepository walletRepository;

  @Test
  public void testGetWalletBalance() {
    Wallet givenWallet = aWallet()
        .withId(walletId)
        .withVersion(9)
        .withCoins(721)
        .withTransaction(
            aTransaction()
                .withId(transactionId)
                .withStatus(ACCEPTED)
                .build()
        )
        .build();

    when(walletRepository.findById(walletId)).thenReturn(of(givenWallet));

    Optional<Wallet> result = walletService.findWalletById(walletId);

    assertThat(result.get()).isEqualTo(givenWallet);
  }

  @Test
  public void testGetNonExistingWallet() {
    when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

    Optional<Wallet> result = walletService.findWalletById(walletId);

    assertThat(result.isEmpty()).isTrue();
  }

  @Test
  public void testCreditWalletCreatesANewWallet() {
    Wallet givenWallet = aWallet()
        .withId(walletId)
        .withVersion(1)
        .withTransaction(aTransaction().build())
        .build();

    when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

    Wallet result = walletService.creditWallet(givenWallet);

    verify(walletRepository).save(givenWallet);
    assertThat(result.getTransaction().getStatus()).isEqualTo(CREATED);
    assertThat(result.getVersion()).isEqualTo(1);
  }

  @Test
  public void testCreditWalletAcceptsSameTransaction() {
    Wallet givenWallet = aWallet()
        .withId(walletId)
        .withVersion(1)
        .withTransaction(aTransaction().withId(transactionId).build())
        .build();
    Wallet existingWallet = aWallet()
        .withId(walletId)
        .withVersion(1)
        .withTransaction(aTransaction().withId(transactionId).build())
        .build();

    when(walletRepository.findById(walletId)).thenReturn(Optional.of(existingWallet));

    Wallet result = walletService.creditWallet(givenWallet);

    assertThat(result.getTransaction().getStatus()).isEqualTo(ACCEPTED);
    verify(walletRepository, Mockito.never()).save(existingWallet);
  }

  @Test
  public void testCreditWalletAddsToExistingBalance() {
    Wallet givenWallet = aWallet()
        .withId(walletId)
        .withVersion(1)
        .withCoins(1000)
        .withTransaction(aTransaction().withId(transactionId).build())
        .build();

    Wallet existingWallet = aWallet()
        .withId(walletId)
        .withVersion(1)
        .withCoins(1000)
        .withTransaction(aTransaction().withId("a1-ss2-a12").build())
        .build();

    when(walletRepository.findById(walletId)).thenReturn(Optional.of(existingWallet));

    Wallet result = walletService.creditWallet(givenWallet);

    assertThat(result.getVersion()).isEqualTo(2);
    assertThat(result.getCoins()).isEqualTo(2000);
    assertThat(result.getTransaction().getId()).isEqualTo(transactionId);
    assertThat(result.getTransaction().getStatus()).isEqualTo(CREATED);

    verify(walletRepository).save(existingWallet);
  }

  @Test
  public void testDebitWalletWithNoBalance() {
    Wallet givenWallet = aWallet()
        .withId(walletId)
        .withTransaction(aTransaction().build())
        .build();

    when(walletRepository.findById(walletId)).thenReturn(Optional.empty());

    Wallet result = walletService.debitWallet(givenWallet);

    assertThat(result.getTransaction().getStatus()).isEqualTo(REJECTED);
  }

  @Test
  public void testDebitWalletWithSameTransaction() {
    Wallet givenWallet = aWallet()
        .withId(walletId)
        .withTransaction(aTransaction().withId(transactionId).build())
        .build();

    Wallet existingWallet = aWallet()
        .withId(walletId)
        .withVersion(2)
        .withTransaction(aTransaction().withId(transactionId).build())
        .build();

    when(walletRepository.findById(walletId)).thenReturn(Optional.of(existingWallet));

    Wallet result = walletService.debitWallet(givenWallet);

    assertThat(result.getTransaction().getStatus()).isEqualTo(ACCEPTED);
    assertThat(result).isEqualTo(existingWallet);
    verify(walletRepository, Mockito.never()).save(existingWallet);
  }

  @Test
  public void testDebitWalletWithInsufficientFunds() {
    Wallet givenWallet = aWallet()
        .withId(walletId)
        .withCoins(101)
        .withTransaction(aTransaction().withId(transactionId).build())
        .build();

    Wallet existingWallet = aWallet()
        .withId(walletId)
        .withVersion(2)
        .withCoins(100)
        .withTransaction(aTransaction().withId("a1s1-123s-asd2").build())
        .build();

    when(walletRepository.findById(walletId)).thenReturn(Optional.of(existingWallet));

    Wallet result = walletService.debitWallet(givenWallet);

    assertThat(result.getTransaction().getStatus()).isEqualTo(REJECTED);
    assertThat(result).isEqualTo(existingWallet);
    verify(walletRepository, Mockito.never()).save(existingWallet);
  }

  @Test
  public void testDebitWalletWithSufficientFunds() {
    Wallet givenWallet = aWallet()
        .withId(walletId)
        .withCoins(101)
        .withTransaction(aTransaction().withId(transactionId).build())
        .build();

    Wallet existingWallet = aWallet()
        .withId(walletId)
        .withVersion(2)
        .withCoins(101)
        .withTransaction(aTransaction().withId("a1s1-123s-asd2").build())
        .build();

    when(walletRepository.findById(walletId)).thenReturn(Optional.of(existingWallet));

    Wallet result = walletService.debitWallet(givenWallet);

    assertThat(result.getVersion()).isEqualTo(3);
    assertThat(result.getCoins()).isEqualTo(0);
    assertThat(result.getTransaction().getId()).isEqualTo(transactionId);
    verify(walletRepository).save(existingWallet);
    assertThat(result.getTransaction().getStatus()).isEqualTo(CREATED);
    assertThat(result).isEqualTo(existingWallet);
  }
}
