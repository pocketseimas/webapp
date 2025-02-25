package com.pocketseimas.webapp.cadence

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "cadence")
data class CadenceProperties(
    var url: String = ""
)