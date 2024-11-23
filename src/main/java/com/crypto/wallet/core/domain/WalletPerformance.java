package com.crypto.wallet.core.domain;

import java.math.*;
import java.time.*;

public record WalletPerformance(BigDecimal totalValue,
                                String bestPerformanceAssetSymbol,
                                String bestAssetPerformance,
                                String worstPerformanceAssetSymbol,
                                String worstAssetPerformance,
                                LocalDateTime dateTime) {


  public static final class WalletPerformanceBuilder {
    private BigDecimal totalValue;
    private String bestPerformanceAssetSymbol;
    private String bestAssetPerformance;
    private String worstPerformanceAssetSymbol;
    private String worstAssetPerformance;
    private LocalDateTime dateTime;

    private WalletPerformanceBuilder() {
    }

    public static WalletPerformanceBuilder aWalletPerformance() {
      return new WalletPerformanceBuilder();
    }

    public WalletPerformanceBuilder withTotalValue(BigDecimal totalValue) {
      this.totalValue = totalValue;
      return this;
    }

    public WalletPerformanceBuilder withBestPerformanceAssetSymbol(String bestPerformanceAssetSymbol) {
      this.bestPerformanceAssetSymbol = bestPerformanceAssetSymbol;
      return this;
    }

    public WalletPerformanceBuilder withBestAssetPerformance(String bestAssetPerformance) {
      this.bestAssetPerformance = bestAssetPerformance;
      return this;
    }

    public WalletPerformanceBuilder withWorstPerformanceAssetSymbol(String worstPerformanceAssetSymbol) {
      this.worstPerformanceAssetSymbol = worstPerformanceAssetSymbol;
      return this;
    }

    public WalletPerformanceBuilder withWorstAssetPerformance(String worstAssetPerformance) {
      this.worstAssetPerformance = worstAssetPerformance;
      return this;
    }

    public WalletPerformanceBuilder withDateTime(LocalDateTime dateTime) {
      this.dateTime = dateTime;
      return this;
    }

    public WalletPerformance build() {
      return new WalletPerformance(totalValue, bestPerformanceAssetSymbol, bestAssetPerformance, worstPerformanceAssetSymbol,
          worstAssetPerformance, dateTime);
    }
  }
}
