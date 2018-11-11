package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Test
import com.bmstu.testingsystem.domain.TestResult
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.domain.UserAnswers
import com.bmstu.testingsystem.repositiry.TestResultRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service("testResultService")
class TestResultServiceImpl : TestResultService {

    @Autowired
    private lateinit var testResultRepository: TestResultRepository

    override fun passTest(test: Test, user: User, userAnswers: UserAnswers): TestResult {
        // todo make result string
        val resultString = "empty"

        val result = TestResult(resultString, Date(), test.id, user.id)
        testResultRepository.save(result)
        test.passCount.inc()
        return result
    }
}
