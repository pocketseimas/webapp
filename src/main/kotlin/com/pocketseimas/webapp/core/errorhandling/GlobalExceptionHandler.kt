package com.pocketseimas.webapp.core.errorhandling

import com.fasterxml.jackson.core.JsonParseException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest

@RestControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(JsonParseException::class)
    fun handleExternalApiException(ex: JsonParseException, request: WebRequest): ResponseEntity<ErrorResponse> {
        logger.error("Handling JsonParseException: ${ex.message}", ex)
        val servletRequest = request as ServletWebRequest
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal server error.",
            message = ex.message,
            cause = ex.cause?.toString(),
            stackTrace = ex.stackTrace.joinToString("\n"),
            path = servletRequest.request.requestURI
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(ex: Exception, request: WebRequest): ResponseEntity<ErrorResponse> {
        logger.error("Unhandled exception occurred", ex)
        val servletRequest = request as ServletWebRequest
        val errorResponse = ErrorResponse(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = "Internal Server Error",
            message = ex.message,
            cause = ex.cause?.toString(),
            stackTrace = ex.stackTrace.joinToString("\n"),
            path = servletRequest.request.requestURI
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}