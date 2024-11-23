package com.crypto.wallet.core.ports.spi;

import com.crypto.wallet.core.domain.*;
import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;

public interface AssetPriceHistoryJpaRepository extends JpaRepository<AssetPriceHistory, Long> {

  @Query("""
      SELECT aph
        FROM AssetPriceHistory aph
       WHERE aph.asset.id = :assetId
       ORDER BY aph.timestamp ASC
      """)
  List<AssetPriceHistory> findOldestAssetPricePerAsset(@Param("assetId") Long assetId,
                                                       Pageable pageable);

  @Query("""
      SELECT aph
        FROM AssetPriceHistory aph
       WHERE aph.asset.id = :assetId
       ORDER BY aph.timestamp DESC
      """)
  List<AssetPriceHistory> findLatestAssetPricePerAsset(@Param("assetId") Long assetId,
                                                       Pageable pageable);

  @Query("""
      SELECT aph
        FROM AssetPriceHistory aph
       WHERE aph.asset.id = :assetId
         AND aph.timestamp BETWEEN :startTimestamp AND :endTimestamp
       ORDER BY aph.timestamp DESC
      """)
  List<AssetPriceHistory> findAssetPricePerAssetPerTimestamp(@Param("assetId") Long assetId,
                                                             @Param("startTimestamp") Long startTimestamp,
                                                             @Param("endTimestamp") Long endTimestamp,
                                                             Pageable pageable);
}
