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
        val id: Long = 0,

        @Enumerated(EnumType.STRING)
        val role: UserRole = UserRole.USER,

        @OneToOne(fetch = FetchType.LAZY,
                cascade = [CascadeType.ALL],
                mappedBy = "user")
        val person: Person? = null,

        @OneToMany(fetch = FetchType.LAZY,
                cascade = [CascadeType.ALL],
                mappedBy = "user")
        val test: List<Test> = emptyList()
)
