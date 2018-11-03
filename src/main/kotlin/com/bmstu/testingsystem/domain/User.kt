package com.bmstu.testingsystem.domain

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "user_data")
data class User (
        @GeneratedValue
        @Id
        val id: UUID,

        val username: String,

        val email: String,

        val password: String,

        @Enumerated(EnumType.STRING)
        val role: UserRole
)
