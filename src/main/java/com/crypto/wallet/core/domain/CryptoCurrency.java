package com.crypto.wallet.core.domain;

public enum CryptoCurrency {

  BTC("bitcoin"),
  ETH("ethereum"),
  USDT("tether"),
  BNB("binance-coin"),
  XRP("ripple"),
  ADA("cardano"),
  DOGE("dogecoin"),
  SOL("solana"),
  DOT("polkadot"),
  MATIC("polygon"),
  LTC("litecoin"),
  SHIB("shiba-inu"),
  AVAX("avalanche"),
  TRX("tron"),
  LINK("chainlink"),
  XLM("stellar"),
  ATOM("cosmos"),
  ETC("ethereum-classic"),
  XMR("monero"),
  ALGO("algorand"),
  ICP("internet-computer"),
  VET("vechain"),
  HBAR("hedera"),
  QNT("quant"),
  FIL("filecoin"),
  EOS("eos"),
  AAVE("aave"),
  GRT("the-graph"),
  KSM("kusama"),
  SAND("the-sandbox"),
  AXS("axie-infinity"),
  MANA("decentraland"),
  CHZ("chiliz"),
  XTZ("tezos"),
  NEAR("near-protocol"),
  THETA("theta"),
  FTM("fantom"),
  ZIL("zilliqa"),
  HNT("helium"),
  CAKE("pancakeswap"),
  ENJ("enjin-coin"),
  RUNE("thorchain"),
  ZRX("0x"),
  CRV("curve-dao-token"),
  DYDX("dydx"),
  GALA("gala"),
  LRC("loopring"),
  CSPR("casper"),
  KDA("kadena"),
  AMP("amp"),
  WAVES("waves"),
  FLOW("flow"),
  COMP("compound"),
  ONEINCH("1inch"),
  MINA("mina"),
  ANKR("ankr"),
  UMA("uma"),
  REN("ren"),
  NANO("nano"),
  RSR("reserve-rights"),
  OMG("omg"),
  DASH("dash"),
  CELO("celo"),
  AR("arweave"),
  HOT("holo"),
  IOST("iost"),
  QTUM("qtum"),
  ZEN("horizen"),
  ONT("ontology"),
  FET("fetch.ai"),
  STMX("stormx"),
  LPT("livepeer"),
  KAVA("kava"),
  BNT("bancor"),
  REP("augur"),
  XDC("xdc-network"),
  SCRT("secret"),
  ROSE("oasis-network"),
  CVC("civic"),
  WRX("wazirx"),
  SKL("skale"),
  WIN("winklink"),
  CHR("chromia"),
  TEL("telcoin"),
  REEF("reef"),
  MTL("metal"),
  RVN("ravencoin"),
  STORJ("storj"),
  HIVE("hive"),
  COTI("coti"),
  SXP("swipe"),
  XVG("verge"),
  STEEM("steem"),
  DGB("digibyte"),
  STRAX("strax");


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
