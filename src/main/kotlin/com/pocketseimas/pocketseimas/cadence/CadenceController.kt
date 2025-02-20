package com.pocketseimas.pocketseimas.cadence

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/cadences")
@Tag(name = "Cadence API", description = "Endpoints for retrieving Seimas cadences")
@SecurityRequirement(name = "apiKey")
class CadenceController(private val cadenceService: CadenceService) {

    @Operation(summary = "Get all cadences", description = "Returns a list of Seimas cadences as JSON.")
    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getCadences(): ResponseEntity<CadenceSeimasInfo> {
        val info: CadenceSeimasInfo? = cadenceService.getCadences()
        return if (info != null) {
            ResponseEntity.ok(info)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}