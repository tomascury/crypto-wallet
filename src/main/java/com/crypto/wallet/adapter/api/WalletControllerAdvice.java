package com.crypto.wallet.adapter.api;

import com.crypto.wallet.adapter.api.dto.*;
import java.time.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;

@ControllerAdvice
public class WalletControllerAdvice {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgumentExeptionException(IllegalArgumentException ex, WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage());
    return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
  }
}
