package com.crypto.wallet.core.domain;

import jakarta.persistence.*;
import java.math.*;
import java.util.*;

@Entity
@Table(name = "asset")
public class Asset {
  @Id
  @Column(name = "id", nullable = false)
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

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "asset")
  private List<AssetPriceHistory> assetPriceHistory;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public void setAssetPriceHistory(List<AssetPriceHistory> assetPriceHistory) {
    this.assetPriceHistory = assetPriceHistory;
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
}