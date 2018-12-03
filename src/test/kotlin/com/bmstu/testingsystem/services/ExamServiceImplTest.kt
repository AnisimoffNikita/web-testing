package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.form_data.ExamData
import com.bmstu.testingsystem.form_data.QuestionData
import com.bmstu.testingsystem.domain.*
import com.bmstu.testingsystem.repositiry.ExamRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import java.sql.Date

@RunWith(MockitoJUnitRunner::class)
class ExamServiceImplTest {

    @Mock
    private lateinit var repositoryMock: ExamRepository

    @InjectMocks
    private lateinit var examService: ExamServiceImpl

    private var exams: List<Exam>
    private var id1: UUID
    private var user: User

    init {
        user = User("username", "email", "password")
        val q1 = Question(0, "q1", QuestionType.NO_ANSWER, null, null, "ans")
        val exam1 = Exam(user,"тест", "небольшое описание", Date(123),  listOf(q1))
        exam1.passCount = 1
        exam1.status = ExamStatus.APPROVED
        val exam2 = Exam(user,"иное задание", "большое описание",  Date(123), listOf(q1))
        exam2.passCount = 5
        exam2.status = ExamStatus.APPROVED
        val exam3 = Exam(user,"экзамен", "много заданий", Date(123), listOf(q1))
        exam3.passCount = 10
        exam3.status = ExamStatus.APPROVED
        exams = listOf(exam1, exam2, exam3)
        id1 = exam1.id
    }


    @Test
    fun findById() {
        val exam = exams.first()
        Mockito.`when`<Optional<Exam>>(repositoryMock.findById(id1)).thenReturn(Optional.of(exam))

        val found = examService.findById(id1)

        Assert.assertEquals(found, exam)
        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).findById(id1)
        Mockito.verifyNoMoreInteractions(repositoryMock)
    }

    @Test
    fun findByKeywordSomething() {
        Mockito.`when`<Iterable<Exam>>(repositoryMock.findByStatus(ExamStatus.APPROVED)).thenReturn(exams)

        val found = examService.findByKeyword("описание").toSet()

        Assert.assertEquals(found, setOf(exams[0], exams[1]))
        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).findByStatus(ExamStatus.APPROVED)
        Mockito.verifyNoMoreInteractions(repositoryMock)
    }

    @Test
    fun findByKeywordNothing() {
        Mockito.`when`<Iterable<Exam>>(repositoryMock.findByStatus(ExamStatus.APPROVED)).thenReturn(exams)

        val found = examService.findByKeyword("слово").toSet()

        Assert.assertEquals(found, emptySet<Exam>())
        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).findByStatus(ExamStatus.APPROVED)
        Mockito.verifyNoMoreInteractions(repositoryMock)
    }

    @Test
    fun getTopPopularTest() {
        Mockito.`when`<Iterable<Exam>>(repositoryMock.findByStatus(ExamStatus.APPROVED)).thenReturn(exams)

        val found = examService.getTopPopularExam(2)

        Assert.assertEquals(found[0], exams[2])
        Assert.assertEquals(found[1], exams[1])
        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).findByStatus(ExamStatus.APPROVED)
        Mockito.verifyNoMoreInteractions(repositoryMock)
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
        Mockito.`when`<Exam>(repositoryMock.save(Mockito.any())).thenReturn(exam)

        val examData = ExamData(exam.name, exam.description, questions);

        examService.addExam(examData, user)

        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).save(Mockito.any())
        Mockito.verifyNoMoreInteractions(repositoryMock)
    }

    @Test
    fun removeTest() {
        val exam = exams.first()
        Mockito.doNothing().`when`<ExamRepository>(repositoryMock).setDeletedById(exam.id)

        examService.removeExam(exam.id)

        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).setDeletedById(exam.id)
        Mockito.verifyNoMoreInteractions(repositoryMock)
    }

    @Test
    fun incPasses() {
        val exam = exams.first()
        val examInc = exam.copy()
        examInc.passCount.inc()
        Mockito.`when`<Exam>(repositoryMock.save(examInc)).thenReturn(examInc)

        examService.incPasses(exam)

        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).save(examInc)
        Mockito.verifyNoMoreInteractions(repositoryMock)
    }
}