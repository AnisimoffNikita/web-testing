package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.form_data.Cell
import com.bmstu.testingsystem.form_data.Row
import com.bmstu.testingsystem.form_data.TableData
import com.bmstu.testingsystem.repositiry.ExamRepository
import com.bmstu.testingsystem.repositiry.ExamResultRepository
import com.bmstu.testingsystem.repositiry.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service("tableService")
class TableServiceImpl : TableService {

@Autowired
private lateinit var examRepository: ExamRepository

@Autowired
private lateinit var userRepository: UserRepository


    // все результаты экзамена
    override fun getPassTableForExam(exam: Exam): TableData {
        val headers = arrayListOf("Дата прохождения", "Пользователь", "Результат")
        val rows = arrayListOf<Row>()
        for (res in exam.results) {
            val date = res.passedAt
            val user = userRepository.findById(res.userId)
            if (user.isPresent) {
                val userGet = user.get()
                val row = Row(arrayListOf(
                        Cell(date),
                        Cell(userGet.username),
                        Cell(res.result)
                ))
                rows.add(row)
            }
        }
        return TableData(headers, rows)
    }

    // все результаты юзера
    override fun getPassTableForUser(user: User): TableData {
        val headers = arrayListOf("Дата прохождения", "Тест", "Автор", "Результат")
        val rows = arrayListOf<Row>()
        for (res in user.results) {
            val date = res.passedAt
            val exam = examRepository.findById(res.testId)
            val author = userRepository.findById(res.userId)
            if (exam.isPresent && author.isPresent) {
                val examGet = exam.get()
                val authorGet = author.get()
                val row = Row(arrayListOf(
                        Cell(date),
                        Cell(examGet.name, "/exam_page/" + examGet.id),
                        Cell(authorGet.username),
                        Cell(res.result)
                ))
                rows.add(row)
            }
        }
        return TableData(headers, rows)
    }

    // таблица пользователей (для админа) todo
    override fun getUserTable(): TableData {
        return TableData()
    }

    // таблица тестов (для админа) todo
    override fun getExamTable(): TableData {
        return TableData()
    }
}