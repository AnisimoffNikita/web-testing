package com.bmstu.testingsystem.domain

import java.util.*
import javax.persistence.*


@Entity
@Table(name = "user_data")
data class User (

        val username: String,

        val email: String,

        val password: String,

        @GeneratedValue
        @Id
        val id: UUID = UUID.randomUUID(),

        @Enumerated(EnumType.STRING)
        val role: UserRole = UserRole.USER
)
