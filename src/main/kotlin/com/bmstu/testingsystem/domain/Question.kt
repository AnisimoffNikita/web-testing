package com.bmstu.testingsystem.domain


data class Question (

        val id: Int = 0,

        val questionText: String = "",

        val type: QuestionType = QuestionType.SINGLE_ANSWER,

        // если есть варианты
        val variants: List<String>? = arrayListOf(),

        val correctVariantsId: List<Int>? = arrayListOf(),

        // если нет вариантов
        val correctInputAnswer: String? = ""

)
