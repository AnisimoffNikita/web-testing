package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.services.AuthenticationServiceImpl
import com.bmstu.testingsystem.services.ExamServiceImpl
import com.bmstu.testingsystem.services.TableServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import java.text.DateFormat
import java.util.*

@Controller
class Table {

    @Autowired
    private lateinit var tableService: TableServiceImpl

    @Autowired
    private lateinit var authService: AuthenticationServiceImpl

    @Autowired
    private lateinit var examService: ExamServiceImpl

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                java.util.Date::class.java, CustomDateEditor(DateFormat.getInstance(), true)
        )
    }

    @GetMapping("/my_passed_exams")
    fun getMyPassedExams(model: Model, authentication: Authentication): String {
        val user = authService.getUser(authentication)
        model.addAttribute("title", "Пройденные тесты")
        model.addAttribute("table", tableService.getPassTableForUser(user))
        return "table"
    }

    @GetMapping("/my_exams/results/{id}")
    fun getStatistic(@PathVariable id: UUID, model: Model): String {
        val exam = examService.findById(id) ?: return "redirect:/my_exams"
        model.addAttribute("title", "Результаты теста \"" + exam.name + "\"")
        model.addAttribute("table", tableService.getPassTableForExam(exam))
        return "table"
    }
}
