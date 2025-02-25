package com.pocketseimas.webapp.core.security

import com.pocketseimas.webapp.core.errorhandling.InvalidApiKeyException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class ApiKeyAuthFilter(
    private val apiKeyService: ApiKeyService,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.requestURI
        // Bypass API key authentication for Swagger and API docs endpoints
        if (path.startsWith("/swagger") || path.startsWith("/v3/api-docs") || path.startsWith("/admin")) {
            filterChain.doFilter(request, response)
            return
        }

        val apiKey = request.getHeader("X-API-KEY")
        if (apiKey == null || !apiKeyService.validateApiKey(apiKey)) {
            throw InvalidApiKeyException("Invalid API Key provided")
        }

        // Enables API key authentication
        val authentication = UsernamePasswordAuthenticationToken(apiKey, null, emptyList())
        SecurityContextHolder.getContext().authentication = authentication

        filterChain.doFilter(request, response)
    }
}