package com.pocketseimas.webapp.cadence

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets

@Service
class CadenceService(
    private val properties: CadenceProperties,
    private val restTemplate: RestTemplate,
    private val xmlMapper: XmlMapper
) {

    @Cacheable("cadences")
    fun getCadences(): CadenceSeimasInfo? {
        val responseBytes: ByteArray = restTemplate.getForObject(properties.url, ByteArray::class.java)
            ?: ByteArray(0)
        val xml = String(responseBytes, StandardCharsets.UTF_8)
        return xmlMapper.readValue(xml, CadenceSeimasInfo::class.java)
    }
}