package com.pocketseimas.webapp.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets

@Configuration
class AppConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate().apply {
            messageConverters.removeIf { it is StringHttpMessageConverter }
            messageConverters.add(StringHttpMessageConverter(StandardCharsets.UTF_8))
        }
    }

    // Required, since by defining global xmlMapper responses become XML too
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = ObjectMapper()
        .registerModules(KotlinModule.Builder().build())
        .registerModules(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @Bean
    fun xmlMapper(): XmlMapper = XmlMapper()
}