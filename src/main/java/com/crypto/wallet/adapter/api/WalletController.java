package com.crypto.wallet.adapter.api;

import com.crypto.wallet.adapter.api.dto.*;
import com.crypto.wallet.core.domain.*;
import com.crypto.wallet.core.service.*;
import jakarta.validation.*;
import org.apache.logging.log4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

  private static final Logger LOGGER = LogManager.getLogger(WalletController.class);

  private final WalletService walletService;

  public WalletController(WalletService walletService) {
    this.walletService = walletService;
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createWallet(@Valid @RequestBody final WalletDto walletDto) {
    Wallet savedWallet = walletService.save(walletDto.toDomain());
    return new ResponseEntity<>(WalletDto.toDto(savedWallet), HttpStatus.CREATED);
  }
}
