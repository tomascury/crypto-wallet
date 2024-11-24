package com.crypto.wallet.core.config;

import com.crypto.wallet.adapter.spi.provider.*;
import com.crypto.wallet.core.ports.spi.*;
import org.springframework.boot.context.properties.*;
import org.springframework.context.annotation.*;
import org.springframework.web.client.*;

@Configuration
@EnableConfigurationProperties({AssetMarketProperties.class})
public class AssetMarketConfig {

  @Bean
  AssetMarketDataProvider assetMarketDataProvider(RestTemplate restTemplate, AssetMarketProperties assetMarketProperties) {
    return new CoinCapMarketDataProvider(restTemplate, assetMarketProperties);
  }
}
