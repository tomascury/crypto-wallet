package com.crypto.wallet.adapter.spi;

import com.crypto.wallet.adapter.spi.dto.*;
import com.crypto.wallet.core.domain.*;
import com.crypto.wallet.core.ports.spi.*;
import java.net.*;
import org.springframework.web.client.*;

public class CoinCapMarketDataProvider implements AssetMarketDataProvider {

  private final RestTemplate restTemplate;

  public CoinCapMarketDataProvider(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public AssetMarketData getMarketData(String assetName) {
    URI uri = URI.create("");
    CoinCapDataDto coinCapDataDto = restTemplate.getForObject(uri, CoinCapDataDto.class);
    assert coinCapDataDto != null;

    return coinCapDataDto.toDomain();

  }
}
