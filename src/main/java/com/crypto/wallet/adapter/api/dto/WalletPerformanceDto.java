package com.crypto.wallet.adapter.api.dto;

import com.crypto.wallet.core.domain.*;
import java.math.*;
import java.time.*;

public record WalletPerformanceDto(BigDecimal totalValue,
                                   String bestPerformanceAssetSymbol,
                                   String bestAssetPerformance,
                                   String worstPerformanceAssetSymbol,
                                   String worstAssetPerformance,
                                   LocalDateTime dateTime) {

  public static WalletPerformanceDto toDto(WalletPerformance walletPerformance) {
    return new WalletPerformanceDto(walletPerformance.totalValue(),
        walletPerformance.bestPerformanceAssetSymbol(),
        walletPerformance.bestAssetPerformance(),
        walletPerformance.worstPerformanceAssetSymbol(),
        walletPerformance.worstAssetPerformance(),
        walletPerformance.dateTime());
  }
}
