package com.crypto.wallet.core.ports.spi;

import com.crypto.wallet.core.domain.*;
import java.math.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.annotation.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WalletJpaRepositoryTest {

  @Autowired
  private WalletJpaRepository walletJpaRepository;

  @Test
  @Rollback(value = false)
  public void whenReceivingWalletWithAssets_saveWalletAndAssetsCascade() {
    var btc = getBtc();
    var eth = getEth();
    var wallet = getWallet();

    wallet.addAsset(btc);
    wallet.addAsset(eth);

    walletJpaRepository.save(wallet);
    Assertions.assertThat(wallet.getId()).isGreaterThan(0);
    Assertions.assertThat(wallet.getAssets().get(0).getId()).isGreaterThan(0);
    Assertions.assertThat(wallet.getAssets().get(1).getId()).isGreaterThan(0);
  }

  @Test
  @Rollback(value = false)
  public void whenReceivingPriceHistory_saveWalletAndSaveAssetsPriceHistoryCascade() {
    var btc = getBtc();
    var btcPriceHistory = AssetPriceHistory.AssetPriceHistoryBuilder.anAssetPriceHistory()
        .withPrice(BigDecimal.valueOf(57870.8958))
        .withTimestamp(System.currentTimeMillis())
        .build();

    btc.addPriceHistory(btcPriceHistory);

    var eth = getEth();
    var ethPriceHistory = AssetPriceHistory.AssetPriceHistoryBuilder.anAssetPriceHistory()
        .withPrice(BigDecimal.valueOf(57870.8958))
        .withTimestamp(System.currentTimeMillis())
        .build();

    eth.addPriceHistory(ethPriceHistory);

    var wallet = getWallet();

    wallet.addAsset(btc);
    wallet.addAsset(eth);

    walletJpaRepository.save(wallet);
    Assertions.assertThat(wallet.getId()).isGreaterThan(0);
    Assertions.assertThat(wallet.getAssets().get(0).getId()).isGreaterThan(0);
    Assertions.assertThat(wallet.getAssets().get(0).getAssetPriceHistory().getFirst().getId()).isGreaterThan(0);
    Assertions.assertThat(wallet.getAssets().get(1).getId()).isGreaterThan(0);
    Assertions.assertThat(wallet.getAssets().get(1).getAssetPriceHistory().getFirst().getId()).isGreaterThan(0);
  }

  private static Wallet getWallet() {
    return Wallet.WalletBuilder.aWallet()
        .withName("X938S0")
        .build();
  }

  private static Asset getEth() {
    return Asset.AssetBuilder.anAsset()
        .withSymbol("ETH")
        .withQuantity(BigDecimal.valueOf(4.89532))
        .withPrice(BigDecimal.valueOf(2004.9774))
        .build();
  }

  private static Asset getBtc() {
    return Asset.AssetBuilder.anAsset()
        .withSymbol("BTC")
        .withQuantity(BigDecimal.valueOf(0.12345))
        .withPrice(BigDecimal.valueOf(37870.5058))
        .build();
  }
}