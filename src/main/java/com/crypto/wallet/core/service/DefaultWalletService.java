package com.crypto.wallet.core.service;

import com.crypto.wallet.core.config.*;
import com.crypto.wallet.core.domain.*;
import com.crypto.wallet.core.ports.spi.*;
import java.math.*;
import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.util.*;
import org.springframework.data.domain.*;

public class DefaultWalletService implements WalletService {

  private static final Logger LOGGER = LogManager.getLogger(DefaultWalletService.class);

  WalletJpaRepository walletJpaRepository;
  AssetPriceHistoryJpaRepository assetPriceHistoryJpaRepository;
  AssetMarketProperties assetMarketProperties;

  public DefaultWalletService(WalletJpaRepository walletJpaRepository,
                              AssetPriceHistoryJpaRepository assetPriceHistoryJpaRepository,
                              AssetMarketProperties assetMarketProperties) {
    this.walletJpaRepository = walletJpaRepository;
    this.assetPriceHistoryJpaRepository = assetPriceHistoryJpaRepository;
    this.assetMarketProperties = assetMarketProperties;
  }

  @Override
  public Wallet save(Wallet wallet) {
    if (Strings.isBlank(wallet.getName())) {
      throw new IllegalArgumentException("Wallet name is null.");
    }
    return walletJpaRepository.save(wallet);
  }

  @Override
  public void saveAssetPriceHistory(AssetPriceHistory assetPriceHistory) {
    assetPriceHistoryJpaRepository.save(assetPriceHistory);
  }

  @Override
  public List<Wallet> findAll() {
    return walletJpaRepository.findAll();
  }

  @Override
  public WalletPerformance getWalletPerformance(Long id) {
    return getWalletPerformance(id, LocalDateTime.now());
  }

  @Override
  public WalletPerformance getWalletPerformance(Long id, LocalDateTime dateTime) {
    Instant dateTimeInstant = dateTime.toInstant(ZoneOffset.UTC);
    long startTimestamp = dateTimeInstant.minus(Duration.ofMinutes(assetMarketProperties.updateInterval())).toEpochMilli();
    long endTimestamp = dateTimeInstant.toEpochMilli();
    Wallet wallet = walletJpaRepository.getReferenceById(id);
    AtomicReference<Asset> bestAssetPerformance = new AtomicReference<>();
    AtomicReference<Asset> worstAssetPerformance = new AtomicReference<>();
    AtomicReference<BigDecimal> bestPercentageChange = new AtomicReference<>();
    AtomicReference<BigDecimal> worstPercentageChange = new AtomicReference<>();
    AtomicReference<BigDecimal> totalAmount = new AtomicReference<>(new BigDecimal("0"));
    wallet.getAssets().forEach(asset -> {

      Pageable pageable = Pageable.ofSize(1).withPage(0);
      AssetPriceHistory oldestAssetPricePerAsset =
          assetPriceHistoryJpaRepository.findOldestAssetPricePerAsset(asset.getId(), pageable).getFirst();
      AssetPriceHistory assetPricePerAssetPerTimestamp =
          assetPriceHistoryJpaRepository.findAssetPricePerAssetPerTimestamp(asset.getId(), startTimestamp, endTimestamp, pageable)
              .getFirst();
      BigDecimal initialPrice = oldestAssetPricePerAsset.getPrice();
      BigDecimal currentPrice = assetPricePerAssetPerTimestamp.getPrice();
      BigDecimal priceDifference = currentPrice.subtract(initialPrice);
      BigDecimal percentageChange = priceDifference
          .divide(initialPrice, 10, RoundingMode.HALF_UP)
          .multiply(new BigDecimal("100"))
          .setScale(2, RoundingMode.HALF_UP);

      LOGGER.info("initialPrice:" + initialPrice);
      LOGGER.info("currentPrice:" + currentPrice);
      if (bestPercentageChange.get() == null || bestPercentageChange.get().compareTo(percentageChange) < 0) {
        bestPercentageChange.set(percentageChange);
        bestAssetPerformance.set(asset);
      }

      if (worstPercentageChange.get() == null || worstPercentageChange.get().compareTo(percentageChange) > 0) {
        worstPercentageChange.set(percentageChange);
        worstAssetPerformance.set(asset);
      }
      totalAmount.set(totalAmount.get().add(currentPrice));

    });
    return WalletPerformance.WalletPerformanceBuilder.aWalletPerformance()
        .withTotalValue(totalAmount.get())
        .withBestPerformanceAssetSymbol(bestAssetPerformance.get().getSymbol())
        .withBestAssetPerformance(bestPercentageChange.get().toString())
        .withWorstPerformanceAssetSymbol(worstAssetPerformance.get().getSymbol())
        .withWorstAssetPerformance(worstPercentageChange.get().toString())
        .withDateTime(dateTime)
        .build();
  }

}
