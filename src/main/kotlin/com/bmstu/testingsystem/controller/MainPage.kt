package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.services.ExamServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainPage {

    @Autowired
    private lateinit var testService: ExamServiceImpl

    @GetMapping("/main_page")
    fun getMainPage(model: Model): String {
        model.addAttribute("exams", testService.getTopPopularExam(10))
        return "main_page"
    }
}