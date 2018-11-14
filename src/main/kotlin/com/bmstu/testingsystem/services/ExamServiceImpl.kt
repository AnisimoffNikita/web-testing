package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.repositiry.ExamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.UUID

@Service("testService")
class ExamServiceImpl : ExamService {

    @Autowired
    private lateinit var examRepository: ExamRepository

    override fun findById(id: UUID): Exam? {
        val test = examRepository.findById(id)
        if (!test.isPresent)
            return null
        return test.get()
    }

    override fun findByKeyword(keyword: String): List<Exam> {
        return examRepository.findAll().filter {
            it.name.contains(keyword) || it.description.contains(keyword)
        }
    }

    override fun getTopPopularExam(count: Int): List<Exam> {
        return examRepository.findAll().sortedByDescending {
            it.passCount
        } . take(count)
    }

    override fun addExam(exam: Exam) {
        examRepository.save(exam)
    }

    override fun removeExam(exam: Exam) {
        examRepository.delete(exam)
    }

    override fun incPasses(exam: Exam) {
        exam.passCount.inc()
        examRepository.save(exam)
    }
}