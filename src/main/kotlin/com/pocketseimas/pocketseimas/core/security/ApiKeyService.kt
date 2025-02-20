package com.pocketseimas.pocketseimas.core.security

import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.util.*

@Service
class ApiKeyService(
    private val apiKeyRepository: ApiKeyRepository
) {
    fun generateApiKey(clientName: String): ApiKey {
        val randomKey = generateSecureRandomKey()
        val apiKey = ApiKey(apiKey = randomKey, clientName = clientName)
        return apiKeyRepository.save(apiKey)
    }

    private fun generateSecureRandomKey(): String {
        val secureRandom = SecureRandom()
        val keyBytes = ByteArray(32)
        secureRandom.nextBytes(keyBytes)
        // Using URL-safe Base64 encoding without padding
        return Base64.getUrlEncoder().withoutPadding().encodeToString(keyBytes)
    }

    fun validateApiKey(key: String): Boolean {
        return apiKeyRepository.findByApiKey(key)?.active ?: false
    }
}