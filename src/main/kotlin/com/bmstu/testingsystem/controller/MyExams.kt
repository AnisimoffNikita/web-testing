package com.bmstu.testingsystem.controller

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

    @GetMapping("/my_exams")
    fun getMyTests(model: Model, authentication: Authentication?): String {
        if (authentication == null) {
            return "redirect:/sign_in"
        }
        val user = userService.findByUsername(authentication.name) ?: return "redirect:/sign_in"
        model.addAttribute("exams", user.exams)
        return "my_exams"
    }

    // todo не удаляется тест
    @GetMapping("/my_exams/delete/{id}")
    fun deleteTest(@PathVariable id: UUID, model: Model, authentication: Authentication?): String {
        if (authentication == null) {
            return "redirect:/sign_in"
        }
        val user = userService.findByUsername(authentication.name) ?: return "redirect:/sign_in"
        val test = testService.findById(id)
        if (test != null)
            testService.removeExam(test)
        model.addAttribute("exams", user.exams)
        return "my_exams"
    }

    // todo пока так, чтобы не падало
    @GetMapping("/my_exams/statistic/{id}")
    fun getStatistic(@PathVariable id: UUID, model: Model, authentication: Authentication?): String {
        if (authentication == null) {
            return "redirect:/sign_in"
        }
        val user = userService.findByUsername(authentication.name) ?: return "redirect:/sign_in"
        model.addAttribute("exams", user.exams)
        return "my_exams"
    }
}