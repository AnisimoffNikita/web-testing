package com.bmstu.testingsystem.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "person_data")
data class Person (
        var firstName: String? = null,

        var lastName: String? = null,

        var birthday: Date? = null,

        var avatar: String? = null
) {
    @GeneratedValue
    @Id
    var id: UUID = UUID.randomUUID()
}