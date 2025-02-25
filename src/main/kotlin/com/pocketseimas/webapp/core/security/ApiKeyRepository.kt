package com.pocketseimas.webapp.core.security

import org.springframework.data.jpa.repository.JpaRepository

interface ApiKeyRepository : JpaRepository<ApiKey, Long> {
    fun findByApiKey(key: String): ApiKey?
}