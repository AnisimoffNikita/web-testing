package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.services.ExamServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class MainPage {

    @Autowired
    private lateinit var examService: ExamServiceImpl

    @GetMapping("/main_page")
    fun getMainPage(model: Model): String {
        model.addAttribute("title", "Популярные тесты")
        model.addAttribute("exams", examService.getTopPopularExam(10))
        return "main_page"
    }

    @GetMapping("/search")
    fun getCreateTest(model: Model, @RequestParam("keyword") keyword: String): String {
        model.addAttribute("title", "Результаты поиска")
        model.addAttribute("exams", examService.findByKeyword(keyword))
        return "main_page"
    }
}