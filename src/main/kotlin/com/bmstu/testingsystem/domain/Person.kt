package com.bmstu.testingsystem.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "person_data")
data class Person (
        @GeneratedValue
        @Id
        val id: Long,

        @OneToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,

        val firstName: String?,

        val lastName: String?,

        val birthday: Date?,

        val avatar: String?
)