package com.crypto.wallet.adapter.api.rest.dto;

import java.time.*;

public record ErrorDetails(LocalDateTime dateTime, String message) {
}
