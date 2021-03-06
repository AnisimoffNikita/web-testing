package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.ExamStatus
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.exception.DeletedExamException
import com.bmstu.testingsystem.exception.NoExamException
import com.bmstu.testingsystem.form_data.ExamData
import com.bmstu.testingsystem.repositiry.ExamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.IllegalArgumentException

@Service("examService")
class ExamServiceImpl : ExamService {

    @Autowired
    private lateinit var examRepository: ExamRepository

    override fun findById(id: UUID): Exam {
        val exam = examRepository.findById(id)
        if (!exam.isPresent) throw NoExamException()
        val examGet = exam.get()
        if (examGet.status == ExamStatus.DELETED) throw DeletedExamException()
        return examGet
    }

    override fun findByKeyword(keyword: String): List<Exam> {
        return examRepository.findByStatus(ExamStatus.APPROVED).filter {
            it.name.contains(keyword) || it.description.contains(keyword)
        }
    }

    override fun getTopPopularExam(count: Int): List<Exam> {
        if (count < 0) throw IllegalArgumentException()
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
        if (!examRepository.findById(exam.id).isPresent) throw IllegalArgumentException()
        exam.passCount += 1
        examRepository.save(exam)
    }
}