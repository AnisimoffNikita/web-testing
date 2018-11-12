package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.*
import com.bmstu.testingsystem.repositiry.TestRepository
import com.bmstu.testingsystem.repositiry.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.context.annotation.Bean
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Controller
class TestPage {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var testRepository: TestRepository

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(DateFormat.getInstance(), true)
        )
    }

    @GetMapping("testpage/**")
    fun getTestPage(model: Model, authentication: Authentication?): String {
        val username = authentication?.name
        val user = userRepository.findByUsername(username!!)
        val ql: MutableList<Question> = arrayListOf()

        val al1: MutableList<String> = arrayListOf()
        al1.add("2")
        al1.add("4")

        val al2: MutableList<String> = arrayListOf()
        al2.add("2")
        al2.add("4")

        val question = Question(1,"2*2", QuestionType.SINGLE_ANSWER, al1)
        val question2 = Question(2,"3*2", QuestionType.MULTIPLE_ANSWER, al2)
        val question3 = Question(3,"4*2", QuestionType.NO_ANSWER, null, null,"8")

        ql.add(question)
        ql.add(question2)
        ql.add(question3)

        val test = Test(user!!,"test1", "decsr1", Date(), ql)

        val ual: MutableList<UserAnswer> = arrayListOf()
        for (q in test.questions)
            ual.add(UserAnswer())
        val ua = UserAnswers(arrayOfNulls<UserAnswer>(3).toMutableList())

        model.addAttribute("userAnswers",  ua)
        model.addAttribute("test", test)
        return "testpage"
    }

    @PostMapping("testpage/**")
    fun postTestPage(@ModelAttribute userAnswers: UserAnswers, authentication: Authentication?): String {
        return "testpage"
    }
}