package com.crypto.wallet.core.domain;

import jakarta.persistence.*;
import java.math.*;
import java.util.*;

@Entity
@Table(name = "asset")
public class Asset {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "symbol", nullable = false)
  private String symbol;

  @Column(name = "quantity", nullable = false)
  private BigDecimal quantity;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(name = "wallet_id")
  private Wallet wallet;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AssetPriceHistory> assetPriceHistory = new ArrayList<>();

  public String getSymbol() {
    return symbol;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public Long getId() {
    return id;
  }

  public Wallet getWallet() {
    return wallet;
  }

  public void setWallet(Wallet wallet) {
    this.wallet = wallet;
  }

  public List<AssetPriceHistory> getAssetPriceHistory() {
    return assetPriceHistory;
  }

  public void addPriceHistory(AssetPriceHistory assetPriceHistory) {
    this.assetPriceHistory.add(assetPriceHistory);
    assetPriceHistory.setAsset(this);
  }

  public void removePriceHistory(AssetPriceHistory assetPriceHistory) {
    this.assetPriceHistory.remove(assetPriceHistory);
    assetPriceHistory.setAsset(null);
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Asset asset)) {
      return false;
    }

    return Objects.equals(id, asset.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Asset{" +
        "id=" + id +
        ", symbol='" + symbol + '\'' +
        ", quantity=" + quantity +
        ", price=" + price +
        ", wallet=" + wallet +
        ", assetPriceHistory=" + assetPriceHistory +
        '}';
  }

  public static final class AssetBuilder {
    private String symbol;
    private BigDecimal quantity;
    private BigDecimal price;

    private AssetBuilder() {
    }

    public static AssetBuilder anAsset() {
      return new AssetBuilder();
    }

    public AssetBuilder withSymbol(String symbol) {
      this.symbol = symbol;
      return this;
    }

    public AssetBuilder withQuantity(BigDecimal quantity) {
      this.quantity = quantity;
      return this;
    }

    public AssetBuilder withPrice(BigDecimal price) {
      this.price = price;
      return this;
    }

    public Asset build() {
      Asset asset = new Asset();
      asset.symbol = this.symbol;
      asset.price = this.price;
      asset.quantity = this.quantity;
      return asset;
    }
  }
}