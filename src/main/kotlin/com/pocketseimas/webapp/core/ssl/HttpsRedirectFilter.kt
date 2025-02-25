package com.pocketseimas.webapp.core.ssl

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class HttpsRedirectFilter : Filter {
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse

        if (httpRequest.scheme.equals("http", ignoreCase = true)) {
            val httpsUrl = "https://${httpRequest.serverName}:${8443}${httpRequest.requestURI}" +
                    (httpRequest.queryString?.let { "?$it" } ?: "")
            httpResponse.sendRedirect(httpsUrl)
        } else {
            chain.doFilter(request, response)
        }
    }
}
