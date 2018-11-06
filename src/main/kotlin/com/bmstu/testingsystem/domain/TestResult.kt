package com.bmstu.testingsystem.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "test_result_data")
data class TestResult (
        val result: String,

        val passedAt: Date,

        @Column(name = "test_id")
        var testId: UUID,

        @Column(name = "user_id")
        var userId: UUID
){

    @GeneratedValue
    @Id
    val id: UUID = UUID.randomUUID()

}