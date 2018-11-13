package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Test
import com.bmstu.testingsystem.domain.TestResult
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.domain.UserAnswers

interface TestResultService {
    fun passTest(test: Test, user: User, userAnswers: UserAnswers): TestResult
}
