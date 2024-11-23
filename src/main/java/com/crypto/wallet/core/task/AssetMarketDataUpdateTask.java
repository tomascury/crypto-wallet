package com.crypto.wallet.core.task;

import com.crypto.wallet.core.domain.*;
import com.crypto.wallet.core.ports.spi.*;
import com.crypto.wallet.core.service.*;
import java.util.*;
import org.apache.logging.log4j.*;
import org.springframework.scheduling.annotation.*;

public class AssetMarketDataUpdateTask {

  private static final Logger LOGGER = LogManager.getLogger(AssetMarketDataUpdateTask.class);

  private AssetMarketDataProvider assetMarketDataProvider;
  private WalletService walletService;

  public AssetMarketDataUpdateTask(AssetMarketDataProvider assetMarketDataProvider, WalletService walletService) {
    this.assetMarketDataProvider = assetMarketDataProvider;
    this.walletService = walletService;
  }

  @Scheduled(cron = "${asset.market.data.update.cron}")
  public void update() {
    LOGGER.info("Asset market data update started");
    List<Wallet> wallets = walletService.findAll();
    wallets
        .forEach(wallet -> {
          wallet.getAssets()
              .forEach(asset -> {
                var assetMarketData = assetMarketDataProvider.getMarketData(asset.getName());
                var assetPriceHistory = AssetPriceHistory.AssetPriceHistoryBuilder.anAssetPriceHistory()
                    .withPrice(assetMarketData.price())
                    .withTimestamp(assetMarketData.timestamp())
                    .build();

                asset.setPrice(assetMarketData.price());

                assetPriceHistory.setAsset(asset);
                walletService.saveAssetPriceHistory(assetPriceHistory);
              });
          walletService.save(wallet);
        });

    LOGGER.info("Asset market data update ended");
  }
}
