package com.pocketseimas.pocketseimas.core.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.pocketseimas.pocketseimas.core.errorhandling.ErrorResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper // Can be injected for JSON serialization
) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.contentType = "application/json"
        response.status = HttpStatus.UNAUTHORIZED.value()
        val errorResponse = ErrorResponse(
            status = HttpStatus.UNAUTHORIZED.value(),
            error = "Unauthorized",
            message = authException.message,
            path = request.requestURI,
            cause = authException.cause?.toString(),
            stackTrace = authException.stackTrace.joinToString("\n")
        )
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}