package com.crypto.wallet.core.service;

import com.crypto.wallet.core.domain.*;
import com.crypto.wallet.core.ports.spi.*;
import java.util.*;
import org.apache.logging.log4j.*;
import org.apache.logging.log4j.util.*;

public class DefaultWalletService implements WalletService {

  private static final Logger LOGGER = LogManager.getLogger(DefaultWalletService.class);

  WalletJpaRepository walletJpaRepository;
  AssetPriceHistoryJpaRepository assetPriceHistoryJpaRepository;

  public DefaultWalletService(WalletJpaRepository walletJpaRepository, AssetPriceHistoryJpaRepository assetPriceHistoryJpaRepository) {
    this.walletJpaRepository = walletJpaRepository;
    this.assetPriceHistoryJpaRepository = assetPriceHistoryJpaRepository;
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

}
