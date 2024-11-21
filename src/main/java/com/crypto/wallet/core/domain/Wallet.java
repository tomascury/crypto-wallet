package com.crypto.wallet.core.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "wallet")
public class Wallet {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "wallet")
  private List<Asset> assets;

  public List<Asset> getAssets() {
    return assets;
  }

  public void setAssets(List<Asset> assets) {
    this.assets = assets;
  }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Wallet wallet)) {
      return false;
    }

    return Objects.equals(id, wallet.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return "Wallet{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", assets=" + assets +
        '}';
  }
}
