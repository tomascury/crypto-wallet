package com.crypto.wallet.core.config;

import org.springframework.boot.context.properties.*;

@ConfigurationProperties(prefix = "asset.market.data")
public record AssetMarketProperties(String resourceUri, Long updateInterval, int threadPool) {
}
