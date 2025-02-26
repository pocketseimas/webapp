package com.pocketseimas.webapp.core.security

import com.pocketseimas.webapp.core.errorhandling.ActiveApiKeyExistsException
import org.springframework.stereotype.Service
import java.security.MessageDigest
import java.security.SecureRandom
import java.time.Instant
import java.util.*

@Service
class ApiKeyService(
    private val apiKeyRepository: ApiKeyRepository
) {
    fun generateApiKey(clientName: String, deviceId: String): ApiKey {
        if (hasActiveApiKey(deviceId)) {
            throw ActiveApiKeyExistsException("Device already has an active API key.")
        }

        val randomKey = generateSecureRandomKey()
        val hashKey = hashApiKey(randomKey)
        val hashedApiKey = ApiKey(apiKey = hashKey, clientName = clientName, deviceId = deviceId)
        val unhashedApiKey = ApiKey(apiKey = randomKey, clientName = clientName, deviceId = deviceId)
        apiKeyRepository.save(hashedApiKey)

        return unhashedApiKey
    }

    fun validateApiKey(key: String): Boolean {
        val hashedKey = hashApiKey(key)
        val apiKeyEntity = apiKeyRepository.findByApiKey(hashedKey)
        return apiKeyEntity != null && apiKeyEntity.expirationDate.isAfter(Instant.now()) && apiKeyEntity.active
    }

    fun revokeApiKey(apiKey: String): Boolean {
        val hashedKey = hashApiKey(apiKey)
        val apiKeyEntity = apiKeyRepository.findByApiKey(hashedKey) ?: return false
        apiKeyEntity.active = false
        apiKeyRepository.save(apiKeyEntity)
        return true
    }

    private fun hasActiveApiKey(deviceId: String): Boolean {
        return apiKeyRepository.existsByDeviceIdAndExpirationDateAfter(deviceId, Instant.now())
    }

    private fun generateSecureRandomKey(): String {
        val secureRandom = SecureRandom()
        val keyBytes = ByteArray(32)
        secureRandom.nextBytes(keyBytes)
        // Using URL-safe Base64 encoding without padding
        return Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes)
    }

    private fun hashApiKey(apiKey: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        return Base64.getEncoder().encodeToString(digest.digest(apiKey.toByteArray()))
    }
}