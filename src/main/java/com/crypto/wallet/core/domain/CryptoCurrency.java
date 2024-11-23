package com.crypto.wallet.core.domain;

public enum CryptoCurrency {

  BTC("bitcoin"),
  ETH("ethereum"),
  USDT("tether"),
  XRP("ripple"),
  ADA("cardano"),
  DOGE("dogecoin"),
  SOL("solana"),
  LTC("litecoin");

  private final String fullName;

  CryptoCurrency(String fullName) {
    this.fullName = fullName;
  }

  public String getFullName() {
    return fullName;
  }

  public static String getNameBySymbol(String symbol) {
    for (CryptoCurrency crypto : CryptoCurrency.values()) {
      if (crypto.name().equalsIgnoreCase(symbol)) {
        return crypto.getFullName();
      }
    }
    throw new IllegalArgumentException("Unknown cryptocurrency symbol: " + symbol);
  }
}
