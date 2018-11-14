package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.QuestionType
import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.services.ExamResultServiceImpl
import com.bmstu.testingsystem.services.ExamServiceImpl
import com.bmstu.testingsystem.services.UserServiceImpl
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
    private lateinit var testService: ExamServiceImpl

    @Autowired
    private lateinit var userService: UserServiceImpl

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(DateFormat.getInstance(), true)
        )
    }

    @GetMapping("/createtest")
    fun getCreateTest(model: Model, authentication: Authentication?): String {
        if (authentication == null)
            return "redirect:/sign_in"
        model.addAttribute("test", TestData())
        return "createtest"
    }

    @PostMapping("/createtest")
    fun postCreateTest(@ModelAttribute test: TestData): String {
       // testService.addTest(testData)
        return "createtest" //
    }
}

data class TestData (
        var name: String = "",
        var description: String = "",
        var questions: MutableList<QuestionData> = arrayListOf()
)

data class QuestionData (
        var questionText: String = "",
        var type: Int = 0,
        var variants: MutableList<String> = arrayListOf(),
        var correctVariants: MutableList<Int> = arrayListOf(),
        var correctInputAnswer: String = ""
)