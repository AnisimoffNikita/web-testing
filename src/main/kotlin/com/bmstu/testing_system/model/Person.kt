package com.bmstu.testing_system.model

import java.util.*
import javax.persistence.*

@Entity
data class Person (
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Id
        val id: UUID,

        @OneToOne
        @JoinColumn(name = "user_id")
        val user: User,

        val firstName: String?,

        val lastName: String?,

        val birthday: Date?,

        val avatar: String?
)