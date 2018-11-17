package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.QuestionType
import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.Question
import com.bmstu.testingsystem.domain.User
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

        testService.addExam(exam, owner)

        model.addAttribute("exam", ExamData()) // временный фикс
        return "create_exam" // todo
    }
}

data class ExamData (
        var name: String? = null,
        var description: String? = null,
        var questions: MutableList<QuestionData>? = null
) {
    fun toExam(user: User) : Exam {
        name?: throw NullPointerException()
        description?: throw NullPointerException()
        questions?: throw NullPointerException()

        val questionList = questions!!.mapIndexed { i, q -> q.toQuestion(i) }
        return Exam(user, name!!, description!!, java.sql.Date(System.currentTimeMillis()), questionList)
    }
}

data class QuestionData (
        var questionText: String? = null,
        var type: QuestionType? = null,
        var variants: MutableList<String>? = null,
        var correctVariants: MutableList<Int>? = null,
        var correctInputAnswer: String? = null
) {
    fun toQuestion(id: Int) : Question {
        questionText?: throw NullPointerException()
        type?: throw NullPointerException()
        if (variants != null && correctInputAnswer != null)
            throw IllegalStateException()
        if (variants == null && correctVariants != null)
            throw IllegalStateException()
        if ((correctVariants == null && correctInputAnswer == null) ||
                (correctVariants != null && correctInputAnswer != null))
            throw IllegalStateException()
        return Question(id, questionText!!, type!!, variants, correctVariants, correctInputAnswer)
    }
}