package com.crypto.wallet.core.config;

import com.crypto.wallet.core.ports.spi.*;
import com.crypto.wallet.core.service.*;
import org.springframework.context.annotation.*;

public class WalletConfig {

  @Bean
  WalletService walletService(WalletJpaRepository walletRepository) {
    return new WalletService(walletRepository);
  }

}
