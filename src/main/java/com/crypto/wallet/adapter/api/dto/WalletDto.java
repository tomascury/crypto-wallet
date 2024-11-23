package com.crypto.wallet.adapter.api.dto;

import com.crypto.wallet.adapter.api.validation.*;
import com.crypto.wallet.core.domain.*;
import com.fasterxml.jackson.annotation.*;
import jakarta.validation.*;
import jakarta.validation.constraints.*;
import java.math.*;
import java.util.*;

public record WalletDto(@NotNull(groups = UpdateValidationGroup.class, message = "[id] cannot be null") Long id,
                        @NotBlank(message = "[name] cannot be null") String name,
                        @JsonProperty("assets") @Valid List<AssetDto> assetsDto) {

  public Wallet toDomain() {
    List<Asset> assets = AssetDto.toDomain(this.assetsDto);
    Wallet wallet = Wallet.WalletBuilder.aWallet()
        .withId(this.id)
        .withName(this.name)
        .build();

    for (Asset asset : assets) {
      AssetPriceHistory assetPriceHistory = AssetPriceHistory.AssetPriceHistoryBuilder.anAssetPriceHistory()
          .withPrice(asset.getPrice())
          .withTimestamp(System.currentTimeMillis())
          .build();

      asset.addPriceHistory(assetPriceHistory);
      wallet.addAsset(asset);
    }
    return wallet;
  }

  public static WalletDto toDto(Wallet wallet) {
    List<AssetDto> assets = AssetDto.toDto(wallet.getAssets());
    return new WalletDto(wallet.getId(), wallet.getName(), assets);
  }

  public record AssetDto(@NotNull(groups = UpdateValidationGroup.class, message = "[asset.id] cannot be null") Long id,
                         @NotBlank(message = "[asset.symbol] cannot be null") String symbol,
                         String name,
                         @NotNull(message = "[asset.quantity] cannot be null") BigDecimal quantity,
                         @NotNull(message = "[asset.price] cannot be null") BigDecimal price) {

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
        assetsDto.add(new AssetDto(asset.getId(), asset.getSymbol(), asset.getName(), asset.getQuantity(), asset.getPrice()));
      }
      return assetsDto;
    }
  }

}
