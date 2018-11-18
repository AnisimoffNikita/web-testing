package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.form_data.TableData
import java.util.*

interface TableService {
    // все результаты экзамена
    fun getPassTableForExam(exam: Exam): TableData

    // все результаты юзера
    fun getPassTableForUser(user: User): TableData

    // таблица пользователей (для админа)
    fun getUserTable(): TableData

    // таблица тестов (для админа)
    fun getExamTable(): TableData
}