package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.Question
import com.bmstu.testingsystem.domain.QuestionType
import com.bmstu.testingsystem.domain.User
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

@RunWith(MockitoJUnitRunner::class)
class ExamServiceImplTest {

    @Mock
    private lateinit var repositoryMock: ExamRepository

    @InjectMocks
    private lateinit var examService: ExamServiceImpl

    private var exams: List<Exam>
    private var id1: UUID

    init {
        val user = User("username", "email", "password")
        val q1 = Question(0, "q1", QuestionType.NO_ANSWER, emptyList(), emptyList(), "ans")
        val exam1 = com.bmstu.testingsystem.domain.Exam(user,"тест", "небольшое описание", listOf(q1))
        exam1.passCount = 1
        val exam2 = com.bmstu.testingsystem.domain.Exam(user,"иное задание", "большое описание", listOf(q1))
        exam2.passCount = 5
        val exam3 = com.bmstu.testingsystem.domain.Exam(user,"экзамен", "много заданий", listOf(q1))
        exam3.passCount = 10
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
        Mockito.`when`<Iterable<Exam>>(repositoryMock.findAll()).thenReturn(exams)

        val found = examService.findByKeyword("описание").toSet()

        Assert.assertEquals(found, setOf(exams[0], exams[1]))
        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).findAll()
        Mockito.verifyNoMoreInteractions(repositoryMock)
    }

    @Test
    fun findByKeywordNothing() {
        Mockito.`when`<Iterable<Exam>>(repositoryMock.findAll()).thenReturn(exams)

        val found = examService.findByKeyword("слово").toSet()

        Assert.assertEquals(found, emptySet<Exam>())
        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).findAll()
        Mockito.verifyNoMoreInteractions(repositoryMock)
    }

    @Test
    fun getTopPopularTest() {
        Mockito.`when`<Iterable<Exam>>(repositoryMock.findAll()).thenReturn(exams)

        val found = examService.getTopPopularExam(2)

        Assert.assertEquals(found[0], exams[2])
        Assert.assertEquals(found[1], exams[1])
        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).findAll()
        Mockito.verifyNoMoreInteractions(repositoryMock)
    }

    @Test
    fun addTest() {
        val exam = exams.first()
        Mockito.`when`<Exam>(repositoryMock.save(exam)).thenReturn(exam)

        examService.addExam(exam)

        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).save(exam)
        Mockito.verifyNoMoreInteractions(repositoryMock)
    }

    @Test
    fun removeTest() {
        val exam = exams.first()
        Mockito.doNothing().`when`<ExamRepository>(repositoryMock).delete(exam)

        examService.removeExam(exam)

        Mockito.verify<ExamRepository>(repositoryMock, Mockito.times(1)).delete(exam)
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