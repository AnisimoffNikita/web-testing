package com.bmstu.testingsystem.repositiry

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.ExamStatus
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExamRepository : CrudRepository<Exam, UUID> {
    fun findByStatus(status: ExamStatus): List<Exam>
}