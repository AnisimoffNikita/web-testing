package com.bmstu.testingsystem.integration_tests

import com.bmstu.testingsystem.TestingSystemApplication
import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.ExamStatus
import com.bmstu.testingsystem.domain.Person
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.repositiry.UserRepository
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.runners.MethodSorters
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional
import java.sql.Date
import java.sql.Timestamp
import java.util.*
import javax.persistence.EntityManager
import javax.persistence.Persistence
import javax.persistence.EntityManagerFactory
import java.util.Locale
import java.text.SimpleDateFormat
import java.text.DateFormat





@RunWith(SpringRunner::class)
@TestPropertySource(locations=["classpath:test.properties"])
@SpringBootTest(classes = [TestingSystemApplication::class])
@Transactional
class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository


    @Test
    fun save() {
        val user = User("testuser","testemail","testpass")
        val toDbUser = userRepository.save(user)

        val id = toDbUser.id

        val fromDbUser = userRepository.findById(id)

        assertEquals(Optional.of(toDbUser), fromDbUser)
    }

    @Test
    fun findByUsername() {
        val user = User("admin","admin","admin")

        val fromDbUser = userRepository.findByUsername("admin")

        assertEquals(user, fromDbUser)
    }

    @Test
    fun update() {
        val id = UUID.fromString("7c803c41-ca5f-4e66-9483-7e361db72917")
        val fromDbUser = userRepository.findById(id)

        val user = fromDbUser.get()
        user.username = "newtestuser"

        userRepository.save(user)

        val updatedDbUser = userRepository.findById(id)

        assertEquals(Optional.of(user), updatedDbUser)
    }

    @Test
    fun delete() {
        val id = UUID.fromString("a4c7eb02-1c79-48df-a1a7-7701bb500dcf")
        userRepository.deleteById(id)

        val fromDbUser = userRepository.findById(id)

        val emptyUser: Optional<User> = Optional.empty()

        assertEquals(emptyUser, fromDbUser)
    }

    @Test
    fun findByUsernameWithExams() {
        val user = User("admin","admin","admin")
        val exam = Exam(user,  "тест главный", "большое описание со словом математика", Date(1195333200000), emptyList())

        val fromDbUser = userRepository.findByUsername("admin")

        assertNotNull(fromDbUser)
        fromDbUser!!
        assertEquals(user, fromDbUser)
        val dbExam = fromDbUser.exams.first()
        assertEquals(dbExam.createdAt, exam.createdAt)
    }
}