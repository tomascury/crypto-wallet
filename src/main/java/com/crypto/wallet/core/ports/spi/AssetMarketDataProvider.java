package com.crypto.wallet.core.ports.spi;

import com.crypto.wallet.core.domain.*;

public interface AssetMarketDataProvider {

  AssetMarketData getMarketData(String assetName);

}
