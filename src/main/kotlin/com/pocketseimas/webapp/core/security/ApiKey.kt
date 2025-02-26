package com.pocketseimas.webapp.core.security

import jakarta.persistence.*
import java.time.Instant
import java.time.LocalDateTime

@Entity
@Table(name = "api_keys")
data class ApiKey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "active")
    var active: Boolean = true,

    @Column(name = "api_key", unique = true, nullable = false)
    val apiKey: String,

    @Column(name = "client_name", nullable = false)
    val clientName: String,

    @Column(name = "device_id", nullable = false)
    val deviceId: String,

    @Column(name = "expiration_date", nullable = false)
    val expirationDate: Instant = Instant.now().plusSeconds(30 * 24 * 60 * 60),

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
)