package com.crypto.wallet.adapter.api.dto;

import java.time.*;

public record ErrorDetails(LocalDateTime dateTime, String message) {
}
