package com.crypto.wallet.core.ports.api;


import com.crypto.wallet.core.domain.*;
import java.util.*;

public interface WalletService {

  Wallet save(Wallet wallet);

  void saveAssetPriceHistory(AssetPriceHistory assetPriceHistory);

  List<Wallet> findAll();

  WalletPerformance getWalletPerformance(Long id);

  WalletPerformance getWalletPerformance(Long id, Long timestamp);
}
