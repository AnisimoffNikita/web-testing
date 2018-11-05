package com.bmstu.testingsystem.domain

import org.junit.Test


internal class JpaQuestionConverterJsonTest {
    @Test
    fun convertToDatabaseColumn() {
        val jp = JpaQuestionConverterJson()

        val q = jp.convertToEntityAttribute("[{\"question\":\"q\"}]")

        val z = jp.convertToDatabaseColumn(q!!)

        print(q)
    }

    @Test
    fun convertToEntityAttribute() {
    }
}