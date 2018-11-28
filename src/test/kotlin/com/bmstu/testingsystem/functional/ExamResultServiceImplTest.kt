package com.bmstu.testingsystem.functional

import com.bmstu.testingsystem.TestingSystemApplication
import com.bmstu.testingsystem.domain.*
import com.bmstu.testingsystem.form_data.UserAnswer
import com.bmstu.testingsystem.form_data.UserAnswers
import com.bmstu.testingsystem.repositiry.ExamResultRepository
import com.bmstu.testingsystem.services.ExamResultServiceImpl
import org.junit.Assert
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import java.sql.Date

@RunWith(SpringRunner::class)
@TestPropertySource(locations=["classpath:test.properties"])
@SpringBootTest(classes = [TestingSystemApplication::class])
@Transactional
class ExamResultServiceImplTest {

    @Autowired
    private lateinit var examResultService: ExamResultServiceImpl

    private lateinit var exam: Exam
    private lateinit var user: User

    init {
        user = User("username", "email", "password")
        val q1 = Question(0, "q1", QuestionType.NO_ANSWER, emptyList(), emptyList(), "ans")
        val q2 = Question(1, "q2", QuestionType.SINGLE_ANSWER, listOf("1","2","3"), listOf(0), "")
        val q3 = Question(2, "q2", QuestionType.MULTIPLE_ANSWER, listOf("1","2","3"), listOf(1,2),"")
        exam = com.bmstu.testingsystem.domain.Exam(user,"тест", "небольшое описание", Date(123), listOf(q1, q2, q3))
    }

    @Test
    fun passTestGood() {
        val a1 = UserAnswer(0, null, "ans")
        val a2 = UserAnswer(1, mutableListOf(0), null)
        val a3 = UserAnswer(2, mutableListOf(1, 2), null)
        val userAnswers = UserAnswers(mutableListOf(a1, a2, a3))

        val result = examResultService.passTest(exam, user, userAnswers)

        Assert.assertEquals(result.result, "3/3")
    }

    @Test
    fun passTestBad() {
        val a1 = UserAnswer(0, null, "bad")
        val a2 = UserAnswer(1, mutableListOf(1), null)
        val a3 = UserAnswer(2, mutableListOf(1, 2), null)
        val userAnswers = UserAnswers(mutableListOf(a1, a2, a3))

        val result = examResultService.passTest(exam, user, userAnswers)

        Assert.assertEquals(result.result, "1/3")
    }

    @Test(expected = IllegalStateException::class)
    fun passTestBadState() {
        val a1 = UserAnswer(0, mutableListOf(1), "bad")
        val a2 = UserAnswer(1, mutableListOf(1), "")
        val a3 = UserAnswer(2, mutableListOf(1, 2), "")
        val userAnswers = UserAnswers(mutableListOf(a1, a2, a3))

        examResultService.passTest(exam, user, userAnswers)
    }
}