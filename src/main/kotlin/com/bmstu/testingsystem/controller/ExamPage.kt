package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.*
import com.bmstu.testingsystem.services.AuthenticationServiceImpl
import com.bmstu.testingsystem.services.ExamResultServiceImpl
import com.bmstu.testingsystem.services.ExamServiceImpl
import com.bmstu.testingsystem.services.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import java.text.DateFormat
import java.util.*

@Controller
class ExamPage {

    @Autowired
    lateinit var testService: ExamServiceImpl

    @Autowired
    lateinit var resultService: ExamResultServiceImpl

    @Autowired
    lateinit var userService: UserServiceImpl

    @Autowired
    private lateinit var authService: AuthenticationServiceImpl

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(DateFormat.getInstance(), true)
        )
    }

    @GetMapping("exam_page/{id}")
    fun getTestPage(@PathVariable id: UUID, model: Model, authentication: Authentication): String {
        val test = testService.findById(id) ?: return "redirect:/main_page"
        val ua = UserAnswers(arrayOfNulls<UserAnswer>(test.questions.size).toMutableList())

        model.addAttribute("userAnswers",  ua)
        model.addAttribute("exam", test)
        return "exam_page"
    }

    @PostMapping("exam_page/{id}")
    fun postTestPage(@PathVariable id: UUID,
                     @ModelAttribute userAnswers: UserAnswers,
                     authentication: Authentication,
                     model: Model): String {
        val user = authService.getUser(authentication)
        val test = testService.findById(id) ?: return "redirect:/main_page"

        val testResult = resultService.passTest(test, user, userAnswers)
        testService.incPasses(test)

        model.addAttribute("res", testResult)
        return "result"

    }
}