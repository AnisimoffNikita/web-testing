package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.form_data.UserAnswer
import com.bmstu.testingsystem.form_data.UserAnswers
import com.bmstu.testingsystem.services.AuthenticationServiceImpl
import com.bmstu.testingsystem.services.ExamResultServiceImpl
import com.bmstu.testingsystem.services.ExamServiceImpl
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
    lateinit var examService: ExamServiceImpl

    @Autowired
    lateinit var resultService: ExamResultServiceImpl

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
        val exam = examService.findById(id)
        val ua = UserAnswers(arrayOfNulls<UserAnswer>(exam.questions.size).toMutableList())

        model.addAttribute("userAnswers",  ua)
        model.addAttribute("exam", exam)

        return "exam_page"
    }

    @PostMapping("exam_page/{id}")
    fun postTestPage(@PathVariable id: UUID,
                     @ModelAttribute userAnswers: UserAnswers,
                     authentication: Authentication,
                     model: Model): String {
        val exam = examService.findById(id)
        val user = authService.getUser(authentication)
        val testResult = resultService.passTest(exam, user, userAnswers)
        examService.incPasses(exam)

        model.addAttribute("res", testResult)
        return "result"

    }
}