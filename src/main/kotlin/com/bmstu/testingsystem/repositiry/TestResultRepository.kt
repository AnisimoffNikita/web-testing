package com.bmstu.testingsystem.repositiry

import com.bmstu.testingsystem.domain.TestResult
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TestResultRepository : CrudRepository<TestResult, UUID> {

}