package com.crypto.wallet.core.domain;

import jakarta.persistence.*;
import java.math.*;
import java.util.*;

@Entity
@Table(name = "asset_price_history")
public class AssetPriceHistory {

  @Id
  @Column(name = "id", nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "asset_id")
  private Asset asset;

  @Column(name = "price", nullable = false)
  private BigDecimal price;

  @Column(name = "timestamp", nullable = false)
  private Long timestamp;

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
}
