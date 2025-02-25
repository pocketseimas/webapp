package com.pocketseimas.webapp.core.security

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class ApiKeyRequest(val clientName: String)
data class ApiKeyResponse(val clientName: String, val apiKey: String)

@RestController
@RequestMapping("/admin/api-keys")
class ApiKeyController(
    private val apiKeyService: ApiKeyService
) {

    @PostMapping("/generate")
    fun generateApiKey(@RequestBody request: ApiKeyRequest): ResponseEntity<ApiKeyResponse> {
        val apiKey = apiKeyService.generateApiKey(request.clientName)
        return ResponseEntity.ok(ApiKeyResponse(apiKey.clientName, apiKey.apiKey))
    }
}