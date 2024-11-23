package com.crypto.wallet.core.config;

import com.crypto.wallet.core.ports.spi.*;
import com.crypto.wallet.core.service.*;
import com.crypto.wallet.core.task.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;

@Configuration
@EnableConfigurationProperties({AssetMarketProperties.class})
public class TaskConfig {

  @Bean
  AssetMarketDataUpdateTask assetMarketDataUpdateTask(AssetMarketDataProvider assetMarketDataProvider,
                                                      WalletService walletService,
                                                      AssetMarketProperties assetMarketProperties) {
    return new AssetMarketDataUpdateTask(assetMarketDataProvider, walletService, assetMarketProperties);
  }

}
