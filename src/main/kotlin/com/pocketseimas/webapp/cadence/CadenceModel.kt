package com.pocketseimas.webapp.cadence

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement

@JacksonXmlRootElement(localName = "SeimoInformacija")
data class CadenceSeimasInfo(
    @JacksonXmlProperty(isAttribute = true, localName = "pavadinimas")
    val name: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "suformavimo_laikas")
    val createdAt: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "šaltinis")
    val source: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "tiekėjas")
    val provider: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "licencija")
    val license: String? = null,

    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "SeimoKadencija")
    val cadences: List<Cadence>? = null
)

data class Cadence(
    @JacksonXmlProperty(isAttribute = true, localName = "kadencijos_id")
    val id: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "pavadinimas")
    val name: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "data_nuo")
    val from: String? = null,

    @JacksonXmlProperty(isAttribute = true, localName = "data_iki")
    val to: String? = null
)