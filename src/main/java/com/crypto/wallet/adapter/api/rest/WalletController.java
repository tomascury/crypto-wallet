package com.crypto.wallet.adapter.api.rest;

import com.crypto.wallet.adapter.api.rest.dto.*;
import com.crypto.wallet.adapter.api.rest.validation.*;
import com.crypto.wallet.core.ports.api.*;
import jakarta.validation.*;
import org.apache.logging.log4j.*;
import org.springframework.http.*;
import org.springframework.validation.annotation.*;
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
  public ResponseEntity<Object> createWallet(@Validated(CreateValidationGroup.class) @RequestBody @Valid final WalletDto walletDto) {
    var savedWallet = walletService.save(walletDto.toDomain());
    return new ResponseEntity<>(WalletDto.toDto(savedWallet), HttpStatus.CREATED);
  }

  @PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> updateWallet(@Validated(UpdateValidationGroup.class) @RequestBody @Valid final WalletDto walletDto) {
    var savedWallet = walletService.save(walletDto.toDomain());
    return new ResponseEntity<>(WalletDto.toDto(savedWallet), HttpStatus.OK);
  }

  @GetMapping("/performance/{id}")
  public ResponseEntity<Object> getWalletPerformance(@PathVariable Long id) {
    var walletPerformance = walletService.getWalletPerformance(id);
    return new ResponseEntity<>(WalletPerformanceDto.toDto(walletPerformance), HttpStatus.OK);
  }

  @GetMapping("/performance/{id}/{timestamp}")
  public ResponseEntity<Object> getWalletPerformance(@PathVariable Long id, @PathVariable Long timestamp) {
    var walletPerformance = walletService.getWalletPerformance(id, timestamp);
    return new ResponseEntity<>(WalletPerformanceDto.toDto(walletPerformance), HttpStatus.OK);
  }

}
