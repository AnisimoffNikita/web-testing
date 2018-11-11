package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Test
import com.bmstu.testingsystem.domain.TestResult
import com.bmstu.testingsystem.domain.User

import java.util.UUID

interface TestService {
    fun findById(id: String): Test?

    fun findByKeyword(keyword: String): Iterable<Test>

    fun getTopPopularTest(count: Int): Iterable<Test>

    fun addTest(test: Test)

    fun removeTest(test: Test)
}
