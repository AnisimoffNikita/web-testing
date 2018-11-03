package com.bmstu.testing_system.model

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class User (
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    val id: UUID,

    val login: String,

    val email: String,

    val password: String
)