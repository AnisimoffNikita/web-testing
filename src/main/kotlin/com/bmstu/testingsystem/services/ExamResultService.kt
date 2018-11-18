package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.ExamResult
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.form_data.UserAnswers

interface ExamResultService {
    fun passTest(exam: Exam, user: User, userAnswers: UserAnswers): ExamResult
}
