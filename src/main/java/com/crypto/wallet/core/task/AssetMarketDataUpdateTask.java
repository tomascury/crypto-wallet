package com.crypto.wallet.core.task;

import com.crypto.wallet.core.ports.spi.*;
import com.crypto.wallet.core.service.*;
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
    

    LOGGER.info("Asset market data update ended");
  }
}
