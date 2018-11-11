package com.bmstu.testingsystem.domain


data class UserAnswers (

        var list: ArrayList<UserAnswer> = arrayListOf()

) {
    fun addAnswer(ans: UserAnswer) : UserAnswer {
        list.add(ans)
        return ans
    }

    fun findByQuestionId(id: Int) : UserAnswer? {
        for (ua : UserAnswer in list) {
            if (ua.questionId == id)
                return ua
        }
        return null
    }
}

data class UserAnswer (

        val questionId: Int? = null,

        val checkedVariants: List<Int>? = null, // если есть варианты ответа

        val inputAnswer: String? = null // если нет вариантов

) {

}
