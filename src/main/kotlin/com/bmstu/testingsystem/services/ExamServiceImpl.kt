package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.ExamStatus
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.form_data.ExamData
import com.bmstu.testingsystem.repositiry.ExamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.UUID

@Service("examService")
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
        return examRepository.findByStatus(ExamStatus.APPROVED).filter {
            it.name.contains(keyword) || it.description.contains(keyword)
        }
    }

    override fun getTopPopularExam(count: Int): List<Exam> {
        return examRepository.findByStatus(ExamStatus.APPROVED).sortedByDescending {
            it.passCount
        } . take(count)
    }

    override fun getAllPendingExams(): List<Exam> {
        return examRepository.findByStatus(ExamStatus.PENDING)
    }

    override fun addExam(exam: ExamData, owner: User) : Exam {
        return examRepository.save(exam.toExam(owner))
    }

    override fun removeExam(id: UUID) {
        examRepository.setDeletedById(id)
    }

    override fun approveExam(id: UUID) {
        examRepository.setApprovedById(id)
    }

    override fun rejectExam(id: UUID) {
        examRepository.setRejectedById(id)
    }

    override fun incPasses(exam: Exam) {
        exam.passCount += 1
        examRepository.save(exam)
    }
}