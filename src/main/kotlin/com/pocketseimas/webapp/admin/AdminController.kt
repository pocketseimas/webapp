package com.pocketseimas.webapp.admin

import com.pocketseimas.webapp.core.security.ApiKeyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/api-keys")
class ApiKeyAdminController(private val apiKeyService: ApiKeyService) {

    @PostMapping("/revoke")
    fun revokeApiKey(@RequestParam apiKey: String, @RequestParam pass: String): ResponseEntity<String> {
        if (pass != SECRET) {
            return ResponseEntity.badRequest().body("Invalid password.")
        }

        return if (apiKeyService.revokeApiKey(apiKey)) {
            ResponseEntity.ok("API key revoked successfully.")
        } else {
            ResponseEntity.badRequest().body("API key not found.")
        }
    }

    companion object {
        // TODO: Remove this hardcoded secret key once admin panel is implemented
        private const val SECRET = "supersecret"
    }
}