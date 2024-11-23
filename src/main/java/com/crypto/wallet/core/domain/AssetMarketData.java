package com.crypto.wallet.core.domain;

import java.math.*;

public record AssetMarketData(String id, BigDecimal price, Long timestamp) {


  public static final class AssetMarketDataBuilder {
    private String id;
    private BigDecimal price;
    private Long timestamp;

    private AssetMarketDataBuilder() {
    }

    public static AssetMarketDataBuilder anAssetMarketData() {
      return new AssetMarketDataBuilder();
    }

    public AssetMarketDataBuilder withId(String id) {
      this.id = id;
      return this;
    }

    public AssetMarketDataBuilder withPrice(BigDecimal price) {
      this.price = price;
      return this;
    }

    public AssetMarketDataBuilder withTimestamp(Long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public AssetMarketData build() {
      return new AssetMarketData(id, price, timestamp);
    }
  }
}
