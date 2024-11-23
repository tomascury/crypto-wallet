package com.crypto.wallet.core.ports.spi;

import com.crypto.wallet.core.domain.*;

public interface AssetMarketDataProvider {

  default AssetMarketData getMarketData(String assetName) {
    throw new IllegalStateException("Resource not implemented");
  }

}
