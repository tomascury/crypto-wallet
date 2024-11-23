package com.crypto.wallet.core.ports.spi;

import com.crypto.wallet.core.domain.*;
import org.springframework.data.jpa.repository.*;

public interface AssetPriceHistoryJpaRepository extends JpaRepository<AssetPriceHistory, Long> {
}
