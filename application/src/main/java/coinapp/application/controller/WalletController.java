package coinapp.application.controller;

import static coinapp.domain.model.TransactionStatus.ACCEPTED;
import static coinapp.domain.model.TransactionStatus.REJECTED;

import coinapp.application.dto.WalletDto;
import coinapp.application.service.WalletMapper;
import coinapp.domain.model.Wallet;
import coinapp.domain.service.WalletService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * WalletController
 *
 * @author Florian Popa fpopa1991@gmail.com
 */
@RestController
@RequestMapping("/wallets")
public class WalletController {

  private Logger logger = Logger.getLogger(WalletController.class.getName());

  @Autowired
  private WalletService walletService;

  @Autowired
  private WalletMapper walletMapper;

  @GetMapping("/{walletId}")
  public ResponseEntity<WalletDto> getWallet(@PathVariable("walletId") final String walletId) {
    logger.log(Level.INFO, "Query wallet {0}", walletId);

    return walletService.findWalletById(walletId)
        .map(wallet -> ResponseEntity.ok(walletMapper.toDto(wallet)))
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping("/{walletId}/credit")
  public ResponseEntity<WalletDto> creditWallet(@PathVariable("walletId") final String walletId, @RequestBody WalletDto walletDto) {
    logger.log(Level.INFO, "Crediting wallet {0} with body {1}", new Object[]{walletId, walletDto});

    walletDto.setId(walletId);

    Wallet wallet = walletService.creditWallet(walletMapper.fromDto(walletDto));

    return getResponse(wallet);
  }

  @PostMapping("/{walletId}/debit")
  public ResponseEntity<WalletDto> debitWallet(@PathVariable("walletId") final String walletId, @RequestBody WalletDto walletDto) {
    logger.log(Level.INFO, "Debiting wallet {0} with body {1}", new Object[]{walletId, walletDto});

    walletDto.setId(walletId);

    Wallet wallet = walletService.debitWallet(walletMapper.fromDto(walletDto));

    return getResponse(wallet);
  }

  private ResponseEntity<WalletDto> getResponse(Wallet wallet) {
    if (wallet.getTransaction().getStatus().equals(REJECTED)) {
      return ResponseEntity.badRequest().build();
    }

    // FIXME obs. 201 and 202 do not contain bodies
    return new ResponseEntity<WalletDto>(walletMapper.toDto(wallet),
        wallet.getTransaction().getStatus().equals(ACCEPTED) ? HttpStatus.ACCEPTED : HttpStatus.CREATED);
  }
}
