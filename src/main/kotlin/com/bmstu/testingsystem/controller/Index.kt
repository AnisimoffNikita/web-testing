package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.TestResult
import com.bmstu.testingsystem.repositiry.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.util.*

@Controller
class Index {

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/")
    fun index(authentication: Authentication?): String {
        val username = authentication?.name


        return "index"
    }

    // тестила thymeleaf и mapping
    // можно удалить

    @GetMapping("/mainpage")
    fun getMainpage(model: Model?): String {
        val x = userRepository.findByUsername("admin");

        if (x != null) {
            val id = x.tests.first().id

            x.results.add(TestResult("12", Date(), id, x.id))

            userRepository.save(x)
        }

        model?.addAttribute("testname", "Название")
        model?.addAttribute("creationDate", "01.01.1900")
        model?.addAttribute("authorName", "Имя")
        model?.addAttribute("testDescription", "Описание теста")
        model?.addAttribute("passCount", "10")
        model?.addAttribute("testId", "0")
        return "mainpage"
    }

    @GetMapping("/testpage")
    fun getTestPage(model: Model?): String {
        return "testpage"
    }

    @GetMapping("/editprofile")
    fun getEditProfile(model: Model?): String {
        return "editprofile"
    }

    @GetMapping("/createtest")
    fun getCreateTest(model: Model?): String {
        return "createtest"
    }

    @GetMapping("/mytests")
    fun getMyTests(model: Model?): String {
        return "mytests"
    }

}