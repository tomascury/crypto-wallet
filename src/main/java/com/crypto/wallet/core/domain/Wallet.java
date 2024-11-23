package com.crypto.wallet.core.domain;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "wallet")
public class Wallet {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Asset> assets = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<Asset> getAssets() {
    return assets;
  }

  public void addAsset(Asset asset) {
    getAssets().add(asset);
    asset.setWallet(this);
  }

  public void removeAsset(Asset asset) {
    getAssets().remove(asset);
    asset.setWallet(null);
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


  public static final class WalletBuilder {
    private Long id;
    private String name;

    private WalletBuilder() {
    }

    public static WalletBuilder aWallet() {
      return new WalletBuilder();
    }

    public WalletBuilder withId(Long id) {
      this.id = id;
      return this;
    }

    public WalletBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public Wallet build() {
      Wallet wallet = new Wallet();
      wallet.name = this.name;
      wallet.id = this.id;
      return wallet;
    }
  }
}
