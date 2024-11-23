package com.crypto.wallet.core.service;


import com.crypto.wallet.core.domain.*;
import java.time.*;
import java.util.*;

public interface WalletService {

  Wallet save(Wallet wallet);

  void saveAssetPriceHistory(AssetPriceHistory assetPriceHistory);

  List<Wallet> findAll();

  WalletPerformance getWalletPerformance(Long id);

  WalletPerformance getWalletPerformance(Long id, LocalDateTime dateTime);
}
