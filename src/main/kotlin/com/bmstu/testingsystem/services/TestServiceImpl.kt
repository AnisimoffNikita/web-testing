package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Test
import com.bmstu.testingsystem.repositiry.TestRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.UUID

@Service("testService")
class TestServiceImpl : TestService {

    @Autowired
    private lateinit var testRepository: TestRepository

    override fun findById(id: String): Test? {
        val test = testRepository.findById(UUID.fromString(id))
        if (!test.isPresent)
            return null
        return test.get()
    }

    override fun findByKeyword(keyword: String): Iterable<Test> {
        // todo search by keyword in test.name and test.description
        return testRepository.findAll()
    }

    override fun getTopPopularTest(count: Int): Iterable<Test> {
        // todo order by pass count
        return testRepository.findAll()
    }

    override fun addTest(test: Test) {
        testRepository.save(test)
    }

    override fun removeTest(test: Test) {
        testRepository.delete(test)
    }
}