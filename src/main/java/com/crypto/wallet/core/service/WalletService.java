package com.crypto.wallet.core.service;


import com.crypto.wallet.core.ports.spi.*;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class WalletService {

  private static final Logger LOGGER = LogManager.getLogger(WalletService.class);

  WalletJpaRepository walletJpaRepository;

  public WalletService(WalletJpaRepository walletJpaRepository) {
    this.walletJpaRepository = walletJpaRepository;
  }

}
