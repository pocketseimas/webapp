package com.pocketseimas.webapp.core.security

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "api_keys")
data class ApiKey(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "api_key", unique = true, nullable = false)
    val apiKey: String,

    @Column(name = "client_name", nullable = false)
    val clientName: String,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "active")
    val active: Boolean = true
)