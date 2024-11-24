package com.crypto.wallet.core.config;

import com.crypto.wallet.core.ports.api.*;
import com.crypto.wallet.core.ports.spi.*;
import com.crypto.wallet.core.service.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

@Configuration
@EnableConfigurationProperties({AssetMarketProperties.class})
public class WalletConfig {

  @Bean
  WalletService walletService(WalletJpaRepository walletRepository,
                              AssetPriceHistoryJpaRepository assetPriceHistoryRepository,
                              AssetMarketProperties assetMarketProperties) {
    return new DefaultWalletService(walletRepository, assetPriceHistoryRepository, assetMarketProperties);
  }

}
