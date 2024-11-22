package com.crypto.wallet.core.domain;

import jakarta.persistence.*;
import java.math.*;
import java.util.*;

@Entity
@Table(name = "asset_price_history")
public class AssetPriceHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "asset_id")
  private Asset asset;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "timestamp", nullable = false)
  private Long timestamp;

  public Long getId() {
    return id;
  }

  public Asset getAsset() {
    return asset;
  }

  public void setAsset(Asset asset) {
    this.asset = asset;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof AssetPriceHistory that)) {
      return false;
    }

    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "AssetPriceHistory{" +
        "id=" + id +
        ", asset=" + asset +
        ", price=" + price +
        ", timestamp=" + timestamp +
        '}';
  }


  public static final class AssetPriceHistoryBuilder {
    private Long id;
    private BigDecimal price;
    private Long timestamp;

    private AssetPriceHistoryBuilder() {
    }

    public static AssetPriceHistoryBuilder anAssetPriceHistory() {
      return new AssetPriceHistoryBuilder();
    }

    public AssetPriceHistoryBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public AssetPriceHistoryBuilder withPrice(BigDecimal price) {
      this.price = price;
      return this;
    }

    public AssetPriceHistoryBuilder withTimestamp(Long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public AssetPriceHistory build() {
      AssetPriceHistory assetPriceHistory = new AssetPriceHistory();
      assetPriceHistory.timestamp = this.timestamp;
      assetPriceHistory.id = this.id;
      assetPriceHistory.price = this.price;
      return assetPriceHistory;
    }
  }
}
