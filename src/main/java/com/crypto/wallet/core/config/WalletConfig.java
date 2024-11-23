package com.crypto.wallet.core.config;

import com.crypto.wallet.core.ports.spi.*;
import com.crypto.wallet.core.service.*;
import org.springframework.context.annotation.*;

@Configuration
public class WalletConfig {

  @Bean
  WalletService walletService(WalletJpaRepository walletRepository, AssetPriceHistoryJpaRepository assetPriceHistoryRepository) {
    return new DefaultWalletService(walletRepository, assetPriceHistoryRepository);
  }

}
