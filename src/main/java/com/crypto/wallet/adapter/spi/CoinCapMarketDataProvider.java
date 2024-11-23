package com.crypto.wallet.adapter.spi;

import com.crypto.wallet.adapter.spi.dto.*;
import com.crypto.wallet.core.config.*;
import com.crypto.wallet.core.domain.*;
import com.crypto.wallet.core.ports.spi.*;
import java.net.*;
import org.apache.logging.log4j.*;
import org.springframework.web.client.*;

public class CoinCapMarketDataProvider implements AssetMarketDataProvider {

  private static final Logger LOGGER = LogManager.getLogger(CoinCapMarketDataProvider.class);

  private final RestTemplate restTemplate;
  private final AssetMarketProperties assetMarketProperties;

  public CoinCapMarketDataProvider(RestTemplate restTemplate, AssetMarketProperties assetMarketProperties) {
    this.restTemplate = restTemplate;
    this.assetMarketProperties = assetMarketProperties;
  }

  @Override
  public AssetMarketData getMarketData(String assetName) {
    var uri = URI.create(assetMarketProperties.resourceUri() + "/assets/" + assetName);
    var coinCapDataDto = restTemplate.getForObject(uri, CoinCapDataDto.class);
    assert coinCapDataDto != null;
    return coinCapDataDto.toDomain();

  }
}
