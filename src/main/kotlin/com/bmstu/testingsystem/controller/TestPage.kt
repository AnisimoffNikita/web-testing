package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.*
import com.bmstu.testingsystem.repositiry.TestRepository
import com.bmstu.testingsystem.repositiry.UserRepository
import com.bmstu.testingsystem.services.TestResultServiceImpl
import com.bmstu.testingsystem.services.TestServiceImpl
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
import kotlin.collections.ArrayList

@Controller
class TestPage {

    /*@Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var testRepository: TestRepository*/

    @Autowired
    lateinit var testService: TestServiceImpl

    @Autowired
    lateinit var resultService: TestResultServiceImpl

    @Autowired
    lateinit var userService: UserServiceImpl

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(DateFormat.getInstance(), true)
        )
    }

    @GetMapping("testpage/{id}")
    fun getTestPage(@PathVariable id: UUID, model: Model, authentication: Authentication?): String {
        /*val username = authentication?.name
        val user = userRepository.findByUsername(username!!)
        val ql: MutableList<Question> = arrayListOf()

        val al1: MutableList<String> = arrayListOf()
        al1.add("2")
        al1.add("4")
        al1.add("6")

        val al2: MutableList<String> = arrayListOf()
        al2.add("2")
        al2.add("4")
        al2.add("6")

        val question = Question(1,"2*2", QuestionType.SINGLE_ANSWER, al1)
        val question2 = Question(2,"3*2", QuestionType.MULTIPLE_ANSWER, al2)
        val question3 = Question(3,"4*2", QuestionType.NO_ANSWER, null, null,"8")

        ql.add(question)
        ql.add(question2)
        ql.add(question3)

        val test = Test(user!!,"test1", "decsr1", Date(), ql)*/

        val test = testService.findById(id) ?: return "redirect:/mainpage"
        val ua = UserAnswers(arrayOfNulls<UserAnswer>(test.questions.size).toMutableList())

        model.addAttribute("userAnswers",  ua)
        model.addAttribute("test", test)
        return "testpage"
    }

    @PostMapping("testpage/{id}")
    fun postTestPage(@PathVariable id: UUID,
                     @ModelAttribute userAnswers: UserAnswers,
                     authentication: Authentication?,
                     model: Model): String {

        if (authentication == null)
            return "redirect:/sign_in"

        val test = testService.findById(id) ?: return "redirect:/mainpage"
        val user = userService.findByUsername(authentication.name) ?: return "redirect:/sign_in"

        val testResult = resultService.passTest(test, user, userAnswers)

        model.addAttribute("res", testResult)
        return "result"

    }
}