package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.services.TestServiceImpl
import com.bmstu.testingsystem.services.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@Controller
class MyTests {

    @Autowired
    private lateinit var userService: UserServiceImpl

    @Autowired
    private lateinit var testService: TestServiceImpl

    @GetMapping("/mytests")
    fun getMyTests(model: Model, authentication: Authentication?): String {
        if (authentication == null) {
            return "redirect:/sign_in"
        }
        val user = userService.findByUsername(authentication.name) ?: return "redirect:/sign_in"
        model.addAttribute("tests", user.tests)
        return "mytests"
    }

    @GetMapping("/mytests/delete/{id}")
    fun deleteTest(@PathVariable id: UUID, model: Model, authentication: Authentication?): String {
        if (authentication == null) {
            return "redirect:/sign_in"
        }
        val user = userService.findByUsername(authentication.name) ?: return "redirect:/sign_in"
        val test = testService.findById(id)
        if (test != null)
            testService.removeTest(test)
        model.addAttribute("tests", user.tests)
        return "mytests"
    }

    // todo пока так, чтобы не падало
    @GetMapping("/mytests/statistic/{id}")
    fun getStatistic(@PathVariable id: UUID, model: Model, authentication: Authentication?): String {
        if (authentication == null) {
            return "redirect:/sign_in"
        }
        val user = userService.findByUsername(authentication.name) ?: return "redirect:/sign_in"
        model.addAttribute("tests", user.tests)
        return "mytests"
    }
}