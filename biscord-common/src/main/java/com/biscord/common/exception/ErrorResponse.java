package com.biscord.common.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
        String error,
        int status,
        LocalDateTime timestamp
) {
}
