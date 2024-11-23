package com.crypto.wallet.core.config;

import com.crypto.wallet.core.ports.spi.*;
import com.crypto.wallet.core.service.*;
import com.crypto.wallet.core.task.*;
import org.springframework.context.annotation.*;

@Configuration
public class TaskConfig {

  @Bean
  AssetMarketDataUpdateTask assetMarketDataUpdateTask(AssetMarketDataProvider assetMarketDataProvider, WalletService walletService) {
    return new AssetMarketDataUpdateTask(assetMarketDataProvider, walletService);
  }
}
