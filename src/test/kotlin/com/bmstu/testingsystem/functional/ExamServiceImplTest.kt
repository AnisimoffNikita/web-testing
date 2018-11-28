package com.bmstu.testingsystem.functional

import com.bmstu.testingsystem.TestingSystemApplication
import com.bmstu.testingsystem.form_data.ExamData
import com.bmstu.testingsystem.form_data.QuestionData
import com.bmstu.testingsystem.domain.*
import com.bmstu.testingsystem.repositiry.ExamRepository
import com.bmstu.testingsystem.services.ExamServiceImpl
import org.junit.Assert
import org.junit.Before
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
import java.util.*
import java.sql.Date

@RunWith(SpringRunner::class)
@TestPropertySource(locations=["classpath:test.properties"])
@SpringBootTest(classes = [TestingSystemApplication::class])
@Transactional
class ExamServiceImplTest {

    @Autowired
    private lateinit var examService: ExamServiceImpl

    private lateinit var exams: List<Exam>
    private lateinit var user1: User
    private lateinit var user2: User

    init {
        user1 = User("admin", "admin", "admin")
        user1.id = UUID.fromString("12412cdb-398f-4def-9cec-325b11968b56")
        user2 = User("admin2", "admin2", "admin2")
        user2.id = UUID.fromString("7c803c41-ca5f-4e66-9483-7e361db72917")
        val exam1 = Exam(user1,"тест главный", "большое описание со словом математика", Date(1195333200000),  listOf())
        exam1.id = UUID.fromString("0596c2c0-a70a-47dd-81c8-31411a5b132a")
        val exam2 = Exam(user2,"тест номер два", "описание теста номер два",  Date(1195333200000), listOf())
        exam2.id = UUID.fromString("66bcd4a3-a3d5-409e-9a38-e0d7b029a020")
        exams = listOf(exam1, exam2)
    }


    @Test
    fun findById() {
        val exam = exams.first()
        val found = examService.findById(exam.id)

        Assert.assertEquals(found, exam)
    }

    @Test
    fun findByKeywordSomething() {
        val found = examService.findByKeyword("описание").toSet()

        Assert.assertEquals(found, setOf(exams[0], exams[1]))
    }

    @Test
    fun findByKeywordNothing() {
        val found = examService.findByKeyword("физика").toSet()

        Assert.assertEquals(found, emptySet<Exam>())
    }

    @Test
    fun getTopPopularTest() {
        val found = examService.getTopPopularExam(2)

        Assert.assertEquals(found[0], exams[1])
        Assert.assertEquals(found[1], exams[0])
    }

    @Test
    fun addTest() {
        val exam = exams.first()
        val questions = exam.questions.map {
            QuestionData(it.questionText,
                    it.type,
                    it.variants?.toMutableList(),
                    it.correctVariantsId?.toMutableList(),
                    it.correctInputAnswer) }.toMutableList()

        val examData = ExamData(exam.name, exam.description, questions);

        examService.addExam(examData, user1)
    }

    @Test
    fun removeTest() {
        val exam = exams.first()

        examService.removeExam(exam.id)
    }

    @Test
    fun incPasses() {
        val exam = exams.first()
        val examInc = exam.copy()
        examInc.passCount.inc()

        examService.incPasses(exam)
    }
}