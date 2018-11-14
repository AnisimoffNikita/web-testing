package com.bmstu.testingsystem.helper

import com.bmstu.testingsystem.domain.Question
import com.bmstu.testingsystem.domain.QuestionType
import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*

class JpaQuestionConverterJsonExam {

    private val q = listOf(Question(0,"123", QuestionType.SINGLE_ANSWER, listOf("1"), listOf(0)))

    private val s = "[{\"id\":0,\"questionText\":\"123\",\"type\":\"SINGLE_ANSWER\",\"variants\":[\"1\"],\"correctVariantsId\":[0],\"correctInputAnswer\":\"\"}]"

    @Test
    fun convertToDatabaseColumn() {
        val conv = JpaQuestionConverterJson()

        val x = conv.convertToDatabaseColumn(q)

        Assert.assertEquals(x, s)
    }

    @Test
    fun convertToEntityAttributeCorrect() {
        val conv = JpaQuestionConverterJson()

        val x = conv.convertToEntityAttribute(s)

        Assert.assertEquals(x, q)
    }

    @Test
    fun convertToEntityAttributeIncorrect() {
        val conv = JpaQuestionConverterJson()

        val x = conv.convertToEntityAttribute("[$s")

        Assert.assertNull(x)
    }
}