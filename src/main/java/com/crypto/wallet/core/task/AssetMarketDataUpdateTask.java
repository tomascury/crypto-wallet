package com.crypto.wallet.core.task;

import com.crypto.wallet.core.config.*;
import com.crypto.wallet.core.domain.*;
import com.crypto.wallet.core.ports.api.*;
import com.crypto.wallet.core.ports.spi.*;
import java.util.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import org.springframework.scheduling.annotation.*;

public class AssetMarketDataUpdateTask {

  private static final Logger LOGGER = LogManager.getLogger(AssetMarketDataUpdateTask.class);

  private final AssetMarketDataProvider assetMarketDataProvider;
  private final WalletService walletService;
  private final AssetMarketProperties assetMarketProperties;

  public AssetMarketDataUpdateTask(AssetMarketDataProvider assetMarketDataProvider,
                                   WalletService walletService,
                                   AssetMarketProperties assetMarketProperties) {
    this.assetMarketDataProvider = assetMarketDataProvider;
    this.walletService = walletService;
    this.assetMarketProperties = assetMarketProperties;
  }

  @Scheduled(cron = "${asset.market.data.update.cron}")
  public void update() {
    try (ExecutorService executorService = Executors.newFixedThreadPool(assetMarketProperties.threadPool())) {
      List<Wallet> wallets = walletService.findAll();
      wallets.forEach(wallet -> {
        wallet.getAssets().forEach(asset -> executorService.submit(() -> updateAssetMarketData(wallet, asset)));
      });
    } catch (Exception e) {
      LOGGER.error("Asset market data update failed: ", e);
    }
  }

  private void updateAssetMarketData(Wallet wallet, Asset asset) {
    LOGGER.info("{} {} Asset market data update started: {}", wallet.getName(), asset.getSymbol(), Thread.currentThread().getName());
    var assetMarketData = assetMarketDataProvider.getMarketData(asset.getName());
    var assetPriceHistory = AssetPriceHistory.AssetPriceHistoryBuilder.anAssetPriceHistory()
        .withPrice(assetMarketData.price())
        .withTimestamp(assetMarketData.timestamp())
        .build();

    asset.setPrice(assetMarketData.price());

    assetPriceHistory.setAsset(asset);
    walletService.saveAssetPriceHistory(assetPriceHistory);
    wallet.addAsset(asset);
    walletService.save(wallet);
    LOGGER.info("{} {} Asset market data update ended: {}", wallet.getName(), asset.getSymbol(), Thread.currentThread().getName());
  }
}
