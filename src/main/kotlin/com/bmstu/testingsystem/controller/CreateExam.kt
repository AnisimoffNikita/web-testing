package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.form_data.ExamData
import com.bmstu.testingsystem.services.AuthenticationServiceImpl
import com.bmstu.testingsystem.services.ExamServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.text.DateFormat
import java.util.*

@Controller
class CreateTest {

    @Autowired
    private lateinit var examService: ExamServiceImpl

    @Autowired
    private lateinit var authService: AuthenticationServiceImpl


    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(DateFormat.getInstance(), true)
        )
    }

    @GetMapping("/create_exam")
    fun getCreateTest(model: Model, authentication: Authentication): String {
        model.addAttribute("exam", ExamData())
        return "create_exam"
    }

    @PostMapping("/create_exam")
    fun postCreateTest(model: Model, @ModelAttribute exam: ExamData, authentication: Authentication): String {
        val owner = authService.getUser(authentication)

        val addExam = examService.addExam(exam, owner)

        model.addAttribute("exam", addExam)
        return "exam_view"
    }
}