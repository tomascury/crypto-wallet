package com.crypto.wallet.core.service;


import com.crypto.wallet.core.domain.*;

public interface WalletService {

  default Wallet save(Wallet wallet) {
    throw new IllegalStateException("Resource not implemented");
  }

}
