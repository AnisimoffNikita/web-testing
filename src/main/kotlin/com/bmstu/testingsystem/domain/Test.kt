package com.bmstu.testingsystem.domain


import java.util.*
import javax.persistence.*
import java.io.IOException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.core.type.TypeReference
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

        val createdAt: Date,


        @Convert(converter = JpaQuestionConverterJson::class)
        val questions: List<Question>,

        @OneToMany(fetch = FetchType.LAZY,
                cascade = [CascadeType.ALL],
                mappedBy = "test")
        val results: List<TestResult> = emptyList()
);

data class Question (
        val question: String? = null
)


@Converter(autoApply = true)
class JpaQuestionConverterJson : AttributeConverter<List<Question>, String> {

    override fun convertToDatabaseColumn(meta: List<Question>): String? {
        try {
            return objectMapper.writeValueAsString(meta)
        } catch (ex: JsonProcessingException) {
            return null
        }

    }

    override fun convertToEntityAttribute(dbData: String): List<Question>? {
        try {
            return objectMapper.readValue(dbData, object : TypeReference<List<Question>>() {})
        } catch (ex: IOException) {
            return null
        }

    }

    companion object {

        private val objectMapper = ObjectMapper()
    }

}

