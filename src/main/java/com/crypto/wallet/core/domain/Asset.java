package com.crypto.wallet.core.domain;

import jakarta.persistence.*;
import java.math.*;
import java.util.*;

@Entity
@Table(name = "asset")
public class Asset {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "symbol", nullable = false)
  private String symbol;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "quantity", precision = 20, scale = 8, nullable = false)
  private BigDecimal quantity;

  @Column(name = "price", precision = 20, scale = 8, nullable = false)
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(name = "wallet_id", nullable = false)
  private Wallet wallet;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<AssetPriceHistory> assetPriceHistory = new ArrayList<>();

  public String getSymbol() {
    return symbol;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
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
        ", name='" + name + '\'' +
        ", quantity=" + quantity +
        ", price=" + price +
        ", wallet=" + wallet +
        ", assetPriceHistory=" + assetPriceHistory +
        '}';
  }

  public static final class AssetBuilder {
    private String symbol;
    private String name;
    private BigDecimal quantity;
    private BigDecimal price;

    private AssetBuilder() {
    }

    public static AssetBuilder anAsset() {
      return new AssetBuilder();
    }

    public AssetBuilder withSymbol(String symbol) {
      this.symbol = symbol;
      this.name = CryptoCurrency.getNameBySymbol(symbol);
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
      asset.name = this.name;
      asset.quantity = this.quantity;
      asset.price = this.price;
      return asset;
    }
  }
}