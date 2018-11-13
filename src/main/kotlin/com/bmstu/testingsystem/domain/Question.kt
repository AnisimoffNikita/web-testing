package com.bmstu.testingsystem.domain


data class Question (

        val id: Int,

        val questionText: String,

        val type: QuestionType,

        // если есть варианты
        val variants: List<String>? = arrayListOf(),

        val correctVariantsId: List<Int>? = arrayListOf(),

        // если нет вариантов
        val correctInputAnswer: String? = ""

)
//
//data class Answer (
//
//        val id: Int,
//
//        val answerText: String
//
//)
