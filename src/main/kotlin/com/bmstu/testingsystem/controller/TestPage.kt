package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.*
import com.bmstu.testingsystem.repositiry.TestRepository
import com.bmstu.testingsystem.repositiry.UserRepository
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
import kotlin.collections.ArrayList

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

    @GetMapping("testpage/{id}")
    fun getTestPage(model: Model, authentication: Authentication?): String {
        val username = authentication?.name
        val user = userRepository.findByUsername(username!!)
        val ql: ArrayList<Question> = arrayListOf()

        val al1: ArrayList<Answer> = arrayListOf()
        al1.add(Answer(1, "2"))
        al1.add(Answer(2, "4"))

        val al2: ArrayList<Answer> = arrayListOf()
        al2.add(Answer(3, "2"))
        al2.add(Answer(4, "4"))

        val question = Question(1,"2*2", QuestionType.SINGLE_ANSWER, al1)
        val question2 = Question(2,"3*2", QuestionType.MULTIPLE_ANSWER, al2)
        val question3 = Question(3,"4*2", QuestionType.NO_ANSWER, null, null,"8")

        ql.add(question)
        ql.add(question2)
        ql.add(question3)

        val test = Test(user!!,"test1", "decsr1", Date(), ql)

        val ual: ArrayList<UserAnswer> = arrayListOf()
        for (q: Question in test.questions)
            ual.add(UserAnswer())
        val ua = UserAnswers(ual)

        model.addAttribute("userAnswers",  ua)
        model.addAttribute("test", test)
        return "testpage"
    }

    @PostMapping("testpage/**")
    fun postTestPage(@ModelAttribute userAnswers: UserAnswers, authentication: Authentication?): String {
        return "testpage"
    }
}