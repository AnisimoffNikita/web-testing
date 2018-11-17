package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.services.AuthenticationServiceImpl
import com.bmstu.testingsystem.services.ExamServiceImpl
import com.bmstu.testingsystem.services.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@Controller
class MyExams {

    @Autowired
    private lateinit var userService: UserServiceImpl

    @Autowired
    private lateinit var testService: ExamServiceImpl

    @Autowired
    private lateinit var authService: AuthenticationServiceImpl

    @GetMapping("/my_exams")
    fun getMyTests(model: Model, authentication: Authentication): String {

        val user = authService.getUser(authentication)
        model.addAttribute("exams", user.exams)
        return "my_exams"
    }

    // todo не удаляется тест
    @GetMapping("/my_exams/delete/{id}")
    fun deleteTest(@PathVariable id: UUID, model: Model, authentication: Authentication?): String {
        val user = authService.getUser(authentication)
        val test = testService.findById(id)
        if (test != null)
            testService.removeExam(test)
        model.addAttribute("exams", user.exams)
        return "my_exams"
    }

    // todo реализовать и перенести в контроллер для табличек
    @GetMapping("/my_exams/statistic/{id}")
    fun getStatistic(@PathVariable id: UUID, model: Model, authentication: Authentication?): String {
        val user = authService.getUser(authentication)
        model.addAttribute("exams", user.exams)
        return "my_exams"
    }
}