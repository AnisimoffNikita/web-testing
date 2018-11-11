package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.services.TestServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class MainPage {

    @Autowired
    private lateinit var testService: TestServiceImpl

    @GetMapping("/mainpage")
    fun getMainPage(model: Model): String {
        model.addAttribute("tests", testService.getTopPopularTest(10))
        return "mainpage"
    }
}