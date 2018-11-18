package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.form_data.ExamData

import java.util.UUID

interface ExamService {
    fun findById(id: UUID): Exam?

    fun findByKeyword(keyword: String): List<Exam>

    fun getTopPopularExam(count: Int): List<Exam>

    fun getAllPendingExams(): List<Exam>

    fun addExam(exam: ExamData, owner: User) : Exam

    fun removeExam(exam: Exam)

    fun approveExam(id: UUID)

    fun rejectExam(id: UUID)

    fun incPasses(exam: Exam)
}
