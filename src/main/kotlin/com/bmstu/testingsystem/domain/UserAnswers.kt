package com.bmstu.testingsystem.domain


data class UserAnswers (
        var list: MutableList<UserAnswer?>? = null
)

data class UserAnswer (
        var questionId: Int? = null,

        var checkedVariants: MutableList<Int>? = null,

        var inputAnswer: String? = null
)