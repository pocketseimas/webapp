package com.pocketseimas.pocketseimas.core.errorhandling

import java.time.LocalDateTime

data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String?,
    val cause: String?,
    val path: String,
    val stackTrace: String?,
)