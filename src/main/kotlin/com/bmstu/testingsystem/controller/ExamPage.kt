package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.*
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

    /*@Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var testRepository: ExamRepository*/

    @Autowired
    lateinit var testService: ExamServiceImpl

    @Autowired
    lateinit var resultService: ExamResultServiceImpl

    @Autowired
    lateinit var userService: UserServiceImpl

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(DateFormat.getInstance(), true)
        )
    }

    @GetMapping("exam_page/{id}")
    fun getTestPage(@PathVariable id: UUID, model: Model, authentication: Authentication?): String {
        val test = testService.findById(id) ?: return "redirect:/main_page"
        val ua = UserAnswers(arrayOfNulls<UserAnswer>(test.questions.size).toMutableList())

        model.addAttribute("userAnswers",  ua)
        model.addAttribute("exam", test)
        return "exam_page"
    }

    @PostMapping("exam_page/{id}")
    fun postTestPage(@PathVariable id: UUID,
                     @ModelAttribute userAnswers: UserAnswers,
                     authentication: Authentication?,
                     model: Model): String {

        if (authentication == null)
            return "redirect:/sign_in"

        val test = testService.findById(id) ?: return "redirect:/main_page"
        val user = userService.findByUsername(authentication.name) ?: return "redirect:/sign_in"

        val testResult = resultService.passTest(test, user, userAnswers)

        model.addAttribute("res", testResult)
        return "result"

    }
}