package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.ExamStatus
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.domain.UserRole
import com.bmstu.testingsystem.form_data.Cell
import com.bmstu.testingsystem.form_data.Row
import com.bmstu.testingsystem.form_data.TableData
import com.bmstu.testingsystem.repositiry.ExamRepository
import com.bmstu.testingsystem.repositiry.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
                        Cell(userGet.username, "/profile_view/${userGet.username}"),
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
            if (exam.isPresent) {
                val examGet = exam.get()
                val authorGet = examGet.user
                val row = Row(arrayListOf(
                        Cell(date),
                        Cell(examGet.name, "/exam_page/${examGet.id}"),
                        Cell(authorGet.username, "/profile_view/${authorGet.username}"),
                        Cell(res.result)
                ))
                rows.add(row)
            }
        }
        return TableData(headers, rows)
    }

    // таблица пользователей (для админа)
    override fun getUserTable(): TableData {
        val headers = arrayListOf("Пользователь", "Роль", "Тесты")
        val rows = arrayListOf<Row>()
        for (usr in userRepository.findAll()) {
            val row = Row(arrayListOf(
                    Cell(usr.username, "/profile_view/${usr.username}"),
                    Cell(if (usr.role == UserRole.USER) {
                        "Пользователь"
                    } else {
                        "Администратор"
                    }),
                    Cell("Просмотреть", "/admin/user_exams/${usr.username}")
            ))
            rows.add(row)
        }
        return TableData(headers, rows)
    }

    override fun getExamTable(): TableData {
        val headers = arrayListOf("Дата создания", "Тест", "Статус", "Число прохождений", "Автор")
        val rows = arrayListOf<Row>()
        for (exam in examRepository.findAll().filter { it -> it.status != ExamStatus.DELETED }) {
            val row = Row(arrayListOf(
                    Cell(exam.createdAt),
                    Cell(exam.name,
                            if (exam.status == ExamStatus.PENDING) {
                                "/admin/exam_view/${exam.id}"
                            } else {
                                "/exam_view/${exam.id}"
                            }),
                    Cell(when (exam.status) {
                        ExamStatus.PENDING -> "Ожидает"
                        ExamStatus.APPROVED -> "Добавлен"
                        ExamStatus.REJECTED -> "Отклонен"
                        else -> throw IllegalStateException()
                    }),
                    Cell(exam.passCount),
                    Cell(exam.user.username, "/profile_view/${exam.user.username}")
            ))
            rows.add(row)
        }
        return TableData(headers, rows)
    }
}