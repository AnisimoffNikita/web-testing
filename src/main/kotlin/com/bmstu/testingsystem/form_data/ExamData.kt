package com.bmstu.testingsystem.form_data

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.Question
import com.bmstu.testingsystem.domain.QuestionType
import com.bmstu.testingsystem.domain.User
import java.sql.Date

data class ExamData (
        var name: String? = null,
        var description: String? = null,
        var questions: MutableList<QuestionData>? = null
) {
    fun toExam(user: User) : Exam {
        name?: throw NullPointerException()
        description?: throw NullPointerException()
        questions?: throw NullPointerException()

        val questionList = questions!!.mapIndexed { i, q -> q.toQuestion(i) }
        return Exam(user, name!!, description!!, Date(System.currentTimeMillis()), questionList)
    }
}

data class QuestionData (
        var questionText: String? = null,
        var type: QuestionType? = null,
        var variants: MutableList<String>? = null,
        var correctVariants: MutableList<Int>? = null,
        var correctInputAnswer: String? = null
) {
    fun toQuestion(id: Int) : Question {
        questionText?: throw NullPointerException()
        type?: throw NullPointerException()
        if (variants != null && correctInputAnswer != null)
            throw IllegalStateException()
        if (variants == null && correctVariants != null)
            throw IllegalStateException()
        if ((correctVariants == null && correctInputAnswer == null) ||
                (correctVariants != null && correctInputAnswer != null))
            throw IllegalStateException()
        return Question(id, questionText!!, type!!, variants, correctVariants, correctInputAnswer)
    }
}