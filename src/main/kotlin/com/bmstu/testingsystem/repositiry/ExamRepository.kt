package com.bmstu.testingsystem.repositiry

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.ExamStatus
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
interface ExamRepository : CrudRepository<Exam, UUID> {
    fun findByStatus(status: ExamStatus): List<Exam>

    @Modifying
    @Query("update exams set status = 'DELETED' where id = ?1",
            nativeQuery = true)
    fun setDeletedById(id: UUID)

    @Modifying
    @Query("update exams set status = 'APPROVED' where id = ?1",
            nativeQuery = true)
    fun setApprovedById(id: UUID)

    @Modifying
    @Query("update exams set status = 'REJECTED' where id = ?1",
            nativeQuery = true)
    fun setRejectedById(id: UUID)
}