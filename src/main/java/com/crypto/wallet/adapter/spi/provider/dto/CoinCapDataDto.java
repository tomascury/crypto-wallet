package com.crypto.wallet.adapter.spi.provider.dto;

import com.crypto.wallet.core.domain.*;
import java.math.*;

public record CoinCapDataDto(Data data, Long timestamp) {

  public record Data(String id, String priceUsd) {

  }

  public AssetMarketData toDomain() {
    return AssetMarketData.AssetMarketDataBuilder.anAssetMarketData()
        .withId(data.id)
        .withPrice(new BigDecimal((data.priceUsd)))
        .withTimestamp(timestamp)
        .build();
  }

}
