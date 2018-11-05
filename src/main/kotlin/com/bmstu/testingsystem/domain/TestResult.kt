package com.bmstu.testingsystem.domain

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "test_result_data")
data class TestResult (
        @GeneratedValue
        @Id
        val id: Long,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "test_id", nullable = false)
        val test: Test,

        val result: String,

        val passedAt: Date
);