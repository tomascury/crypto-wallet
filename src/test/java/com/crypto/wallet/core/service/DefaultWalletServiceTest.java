package com.crypto.wallet.core.service;

import static org.mockito.ArgumentMatchers.*;


import com.crypto.wallet.core.config.*;
import com.crypto.wallet.core.domain.*;
import com.crypto.wallet.core.ports.spi.*;
import java.math.*;
import java.util.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.springframework.data.domain.*;

@ExtendWith(MockitoExtension.class)
class DefaultWalletServiceTest {

  @InjectMocks
  DefaultWalletService walletService;

  @Mock
  WalletJpaRepository walletJpaRepository;
  @Mock
  AssetPriceHistoryJpaRepository assetPriceHistoryJpaRepository;
  @Mock
  AssetMarketProperties assetMarketProperties;

  @BeforeAll
  public static void beforeAll() {
    MockitoAnnotations.openMocks(DefaultWalletServiceTest.class);
  }

  @Test
  void whenReceivingWalletId_getWalletPerformance() {
    var wallet = getWallet();
    var btcOldestPriceHistory = AssetPriceHistory.AssetPriceHistoryBuilder.anAssetPriceHistory()
        .withId(1L)
        .withPrice(BigDecimal.valueOf(25))
        .withTimestamp(System.currentTimeMillis())
        .build();

    var ethOldestPriceHistory = AssetPriceHistory.AssetPriceHistoryBuilder.anAssetPriceHistory()
        .withId(2L)
        .withPrice(BigDecimal.valueOf(15))
        .withTimestamp(System.currentTimeMillis())
        .build();

    var btcLatestPriceHistory = AssetPriceHistory.AssetPriceHistoryBuilder.anAssetPriceHistory()
        .withId(1L)
        .withPrice(BigDecimal.valueOf(30))
        .withTimestamp(System.currentTimeMillis())
        .build();

    var ethLatestPriceHistory = AssetPriceHistory.AssetPriceHistoryBuilder.anAssetPriceHistory()
        .withId(2L)
        .withPrice(BigDecimal.valueOf(20))
        .withTimestamp(System.currentTimeMillis())
        .build();

    Asset eth = getEth();
    eth.addPriceHistory(ethOldestPriceHistory);
    Asset btc = getBtc();
    btc.addPriceHistory(btcOldestPriceHistory);

    wallet.addAsset(btc);
    wallet.addAsset(eth);

    Pageable pageable = Pageable.ofSize(1).withPage(0);
    Mockito.when(walletJpaRepository.getReferenceById(1L)).thenReturn(wallet);
    Mockito.when(assetPriceHistoryJpaRepository.findOldestAssetPricePerAsset(1L, pageable)).thenReturn(List.of(btcOldestPriceHistory));
    Mockito.when(assetPriceHistoryJpaRepository.findOldestAssetPricePerAsset(2L, pageable)).thenReturn(List.of(ethOldestPriceHistory));
    Mockito.when(assetPriceHistoryJpaRepository.findAssetPricePerAssetPerTimestamp(eq(1L), any(), any(), eq(pageable)))
        .thenReturn(List.of(btcLatestPriceHistory));
    Mockito.when(assetPriceHistoryJpaRepository.findAssetPricePerAssetPerTimestamp(eq(2L), any(), any(), eq(pageable)))
        .thenReturn(List.of(ethLatestPriceHistory));

    WalletPerformance walletPerformance = walletService.getWalletPerformance(1L);

    Assertions.assertEquals("ETH", walletPerformance.bestPerformanceAssetSymbol());
    Assertions.assertEquals("33.33", walletPerformance.bestAssetPerformance());
    Assertions.assertEquals("BTC", walletPerformance.worstPerformanceAssetSymbol());
    Assertions.assertEquals("20.00", walletPerformance.worstAssetPerformance());
    Assertions.assertEquals(new BigDecimal("100.00"), walletPerformance.totalValue());
  }

  private static Wallet getWallet() {
    return Wallet.WalletBuilder.aWallet()
        .withId(1L)
        .withName("X938S0")
        .build();
  }

  private static Asset getBtc() {
    return Asset.AssetBuilder.anAsset()
        .withId(1L)
        .withSymbol("BTC")
        .withQuantity(BigDecimal.valueOf(2))
        .withPrice(BigDecimal.valueOf(30))
        .build();
  }

  private static Asset getEth() {
    return Asset.AssetBuilder.anAsset()
        .withId(2L)
        .withSymbol("ETH")
        .withQuantity(BigDecimal.valueOf(2))
        .withPrice(BigDecimal.valueOf(20))
        .build();
  }
}