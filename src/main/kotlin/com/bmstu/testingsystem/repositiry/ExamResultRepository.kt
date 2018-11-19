package com.bmstu.testingsystem.repositiry

import com.bmstu.testingsystem.domain.ExamResult
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional
interface ExamResultRepository : CrudRepository<ExamResult, UUID>