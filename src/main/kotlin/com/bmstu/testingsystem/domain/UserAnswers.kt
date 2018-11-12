package com.bmstu.testingsystem.domain


data class UserAnswers (

        var list: MutableList<UserAnswer?>? = null

) {
}

data class UserAnswer (

        var questionId: Int? = null,

        var checkedVariants: MutableList<Int>? = null, // если есть варианты ответа

        var inputAnswer: String? = null // если нет вариантов

) {

}
