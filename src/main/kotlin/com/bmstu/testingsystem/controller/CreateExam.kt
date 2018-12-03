package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.form_data.ExamData
import com.bmstu.testingsystem.form_data.getPassStatisticDelete
import com.bmstu.testingsystem.services.AuthenticationServiceImpl
import com.bmstu.testingsystem.services.ExamServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.ExceptionHandler
import java.lang.NullPointerException
import java.text.DateFormat
import java.util.*
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse


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
    fun postCreateTest(model: Model, @ModelAttribute("exam")  exam: ExamData, authentication: Authentication): String {
        val owner = authService.getUser(authentication)
        val addExam = examService.addExam(exam, owner)
        model.addAttribute("exam", addExam)
        model.addAttribute("btns", getPassStatisticDelete())
        return "redirect:/exam_view/${addExam.id}"
    }

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(model: Model,response: HttpServletResponse): String{
        model.addAttribute("info", "Некорректный запрос")
        response.status = HttpServletResponse.SC_BAD_REQUEST
        return "error_page"
    }
}