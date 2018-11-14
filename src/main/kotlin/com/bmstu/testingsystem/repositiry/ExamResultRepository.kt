package com.bmstu.testingsystem.repositiry

import com.bmstu.testingsystem.domain.ExamResult
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExamResultRepository : CrudRepository<ExamResult, UUID> {

}