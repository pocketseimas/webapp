package com.pocketseimas.webapp.core.security

import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant

interface ApiKeyRepository : JpaRepository<ApiKey, Long> {
    fun findByApiKey(apiKey: String): ApiKey?
    fun existsByDeviceIdAndExpirationDateAfter(deviceId: String, expirationDate: Instant): Boolean
    fun findByDeviceIdAndExpirationDateAfter(deviceId: String, expirationDate: Instant): ApiKey?
    fun deleteAllByExpirationDateBeforeOrActiveFalse(expirationDate: Instant): Int
}