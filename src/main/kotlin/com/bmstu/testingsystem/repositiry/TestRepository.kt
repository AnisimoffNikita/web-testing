package com.bmstu.testingsystem.repositiry

import com.bmstu.testingsystem.domain.Test
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TestRepository : CrudRepository<Test, UUID> {
}