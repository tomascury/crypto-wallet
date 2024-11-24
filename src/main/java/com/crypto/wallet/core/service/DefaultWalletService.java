package com.crypto.wallet.core.service;

import com.crypto.wallet.core.config.*;
import com.crypto.wallet.core.domain.*;
import com.crypto.wallet.core.ports.api.*;
import com.crypto.wallet.core.ports.spi.*;
import java.math.*;
import java.time.*;
import java.util.*;
import org.apache.logging.log4j.*;
import org.springframework.data.domain.*;

public class DefaultWalletService implements WalletService {

  private static final Logger LOGGER = LogManager.getLogger(DefaultWalletService.class);
  private static final Pageable LIMIT_1 = Pageable.ofSize(1).withPage(0);

  private final WalletJpaRepository walletJpaRepository;
  private final AssetPriceHistoryJpaRepository assetPriceHistoryJpaRepository;
  private final AssetMarketProperties assetMarketProperties;

  public DefaultWalletService(WalletJpaRepository walletJpaRepository,
                              AssetPriceHistoryJpaRepository assetPriceHistoryJpaRepository,
                              AssetMarketProperties assetMarketProperties) {
    this.walletJpaRepository = walletJpaRepository;
    this.assetPriceHistoryJpaRepository = assetPriceHistoryJpaRepository;
    this.assetMarketProperties = assetMarketProperties;
  }

  @Override
  public Wallet save(Wallet wallet) {
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
    return getWalletPerformance(id, LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
  }

  @Override
  public WalletPerformance getWalletPerformance(Long id, Long timeStamp) {
    var wallet = walletJpaRepository.getReferenceById(id);

    var localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneOffset.UTC);
    var dateTimeInstant = localDateTime.toInstant(ZoneOffset.UTC);
    var startTimestamp = dateTimeInstant.minus(Duration.ofMinutes(assetMarketProperties.updateInterval())).toEpochMilli();
    var endTimestamp = dateTimeInstant.toEpochMilli();

    Asset bestAssetPerformance = null;
    Asset worstAssetPerformance = null;
    BigDecimal bestPercentageChange = null;
    BigDecimal worstPercentageChange = null;
    BigDecimal totalValue = new BigDecimal("0");

    for (Asset asset : wallet.getAssets()) {
      var oldestAssetPrice = assetPriceHistoryJpaRepository.findOldestAssetPricePerAsset(asset.getId(), LIMIT_1).getFirst();

      var assetPricePerAssetPerTimestampList =
          assetPriceHistoryJpaRepository.findAssetPricePerAssetPerTimestamp(asset.getId(), startTimestamp, endTimestamp, LIMIT_1);
      var latestPricePerAsset = assetPricePerAssetPerTimestampList.isEmpty() ?
          assetPriceHistoryJpaRepository.findLatestAssetPricePerAsset(asset.getId(), LIMIT_1).getFirst() :
          assetPricePerAssetPerTimestampList.getFirst();

      var initialPrice = oldestAssetPrice.getPrice();
      var currentPrice = latestPricePerAsset.getPrice();
      var percentageChange = calculatePercentageChange(currentPrice, initialPrice);

      if (bestPercentageChange == null || bestPercentageChange.compareTo(percentageChange) < 0) {
        bestPercentageChange = percentageChange;
        bestAssetPerformance = asset;
      }

      if (worstPercentageChange == null || worstPercentageChange.compareTo(percentageChange) > 0) {
        worstPercentageChange = percentageChange;
        worstAssetPerformance = asset;
      }
      totalValue = totalValue.add(currentPrice.multiply(asset.getQuantity()));

      LOGGER.info("initialPrice: [{}] == {}", asset.getSymbol(), initialPrice);
      LOGGER.info("currentPrice: [{}] == {}", asset.getSymbol(), currentPrice);
    }

    assert bestAssetPerformance != null;

    return WalletPerformance.WalletPerformanceBuilder.aWalletPerformance()
        .withTotalValue(totalValue.setScale(2, RoundingMode.HALF_UP))
        .withBestPerformanceAssetSymbol(bestAssetPerformance.getSymbol())
        .withBestAssetPerformance(bestPercentageChange.toString())
        .withWorstPerformanceAssetSymbol(worstAssetPerformance.getSymbol())
        .withWorstAssetPerformance(worstPercentageChange.toString())
        .withDateTime(localDateTime)
        .build();
  }

  private static BigDecimal calculatePercentageChange(BigDecimal currentPrice, BigDecimal initialPrice) {
    var priceDifference = currentPrice.subtract(initialPrice);
    return priceDifference
        .divide(initialPrice, 10, RoundingMode.HALF_UP)
        .multiply(new BigDecimal("100"))
        .setScale(2, RoundingMode.HALF_UP);
  }

}
