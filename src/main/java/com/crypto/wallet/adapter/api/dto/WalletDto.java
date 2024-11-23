package com.crypto.wallet.adapter.api.dto;

import com.crypto.wallet.core.domain.*;
import com.fasterxml.jackson.annotation.*;
import java.math.*;
import java.util.*;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.*;

public record WalletDto(Long id,
                        @NotBlank String name,
                        @JsonProperty("assets") List<AssetDto> assetsDto) {

  public Wallet toDomain() {
    List<Asset> assets = AssetDto.toDomain(this.assetsDto);
    Wallet wallet = Wallet.WalletBuilder.aWallet()
        .withId(this.id)
        .withName(this.name)
        .build();

    for (Asset asset : assets) {
      wallet.addAsset(asset);
    }
    return wallet;
  }

  public static WalletDto toDto(Wallet wallet) {
    List<AssetDto> assets = AssetDto.toDto(wallet.getAssets());
    return new WalletDto(wallet.getId(), wallet.getName(), assets);
  }

  public record AssetDto(Long id, String symbol, BigDecimal quantity, BigDecimal price) {

    public static List<Asset> toDomain(List<AssetDto> assetsDto) {
      List<Asset> assets = new ArrayList<>();
      if (Objects.nonNull(assetsDto) && !assetsDto.isEmpty()) {
        for (AssetDto assetDto : assetsDto) {
          Asset asset = Asset.AssetBuilder.anAsset()
              .withSymbol(assetDto.symbol)
              .withQuantity(assetDto.quantity)
              .withPrice(assetDto.price)
              .build();
          assets.add(asset);
        }
      }
      return assets;
    }

    public static List<AssetDto> toDto(List<Asset> assets) {
      List<AssetDto> assetsDto = new ArrayList<>();
      for (Asset asset : assets) {
        assetsDto.add(new AssetDto(asset.getId(), asset.getSymbol(), asset.getQuantity(), asset.getPrice()));
      }
      return assetsDto;
    }
  }

}
