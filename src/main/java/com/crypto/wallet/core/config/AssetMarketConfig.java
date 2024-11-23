package com.crypto.wallet.core.config;

import com.crypto.wallet.adapter.spi.*;
import com.crypto.wallet.core.ports.spi.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@Configuration
public class AssetMarketConfig {

  @Bean
  AssetMarketDataProvider assetMarketDataProvider(RestTemplate restTemplate) {
    return new CoinCapMarketDataProvider(restTemplate);
  }
}
