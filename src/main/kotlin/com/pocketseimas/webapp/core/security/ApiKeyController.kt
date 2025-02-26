package com.pocketseimas.webapp.core.security

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class ApiKeyRequest(
    @Schema(description = "Name of the client requesting the API key", example = "myClient")
    val clientName: String,

    @Schema(description = "Unique device identifier", example = "device-1234")
    val deviceId: String
)

data class ApiKeyResponse(
    @Schema(description = "Client name associated with the API key", example = "myClient")
    val clientName: String,

    @Schema(description = "Generated API key", example = "a1b2c3d4e5")
    val apiKey: String
)

@RestController
@RequestMapping("/api-keys")
class ApiKeyController(
    private val apiKeyService: ApiKeyService
) {

    @PostMapping("/generate")
    fun generateApiKey(@RequestBody request: ApiKeyRequest): ResponseEntity<ApiKeyResponse> {
        val apiKey = apiKeyService.generateApiKey(request.clientName, request.deviceId)
        return ResponseEntity.ok(ApiKeyResponse(apiKey.clientName, apiKey.apiKey))
    }
}