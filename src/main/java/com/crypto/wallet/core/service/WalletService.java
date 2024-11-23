package com.crypto.wallet.core.service;


import com.crypto.wallet.core.domain.*;
import java.util.*;

public interface WalletService {

  Wallet save(Wallet wallet);

  void saveAssetPriceHistory(AssetPriceHistory assetPriceHistory);

  List<Wallet> findAll();

}
