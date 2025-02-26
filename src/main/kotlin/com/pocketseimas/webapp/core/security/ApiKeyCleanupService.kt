package com.pocketseimas.webapp.core.security

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ApiKeyCleanupService(private val apiKeyRepository: ApiKeyRepository) {

    @Scheduled(cron = "0 0 0 * * ?") // Run daily at midnight
    fun deleteExpiredAndRevokedApiKeys() {
        val deletedCount = apiKeyRepository.deleteAllByExpirationDateBeforeOrActiveFalse(Instant.now())
        println("Deleted $deletedCount expired or revoked API keys.")
    }
}