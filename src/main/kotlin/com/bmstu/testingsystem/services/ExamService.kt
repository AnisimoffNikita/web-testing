package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.controller.ExamData
import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.User

import java.util.UUID

interface ExamService {
    fun findById(id: UUID): Exam?

    fun findByKeyword(keyword: String): List<Exam>

    fun getTopPopularExam(count: Int): List<Exam>

    fun addExam(exam: ExamData, owner: User?) : Exam

    fun removeExam(exam: Exam)

    fun incPasses(exam: Exam)
}
