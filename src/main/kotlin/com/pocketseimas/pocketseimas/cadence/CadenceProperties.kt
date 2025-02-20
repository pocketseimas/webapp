package com.pocketseimas.pocketseimas.cadence

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "cadence")
data class CadenceProperties(
    var url: String = ""
)