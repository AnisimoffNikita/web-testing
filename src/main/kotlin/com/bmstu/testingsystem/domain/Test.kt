package com.bmstu.testingsystem.domain


import java.util.*
import javax.persistence.*
import java.io.IOException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import javax.persistence.AttributeConverter


@Entity
@Table(name = "test_data")
data class Test (
        @GeneratedValue
        @Id
        val id: Long,

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "user_id", nullable = false)
        val user: User,

        val name: String,

        val description: String,

        @Enumerated(EnumType.STRING)
        val status: TestStatus,

        val createdAt: Date

//        @Convert(converter = JpaConverterJson::class)
//        val questions: Question
);
