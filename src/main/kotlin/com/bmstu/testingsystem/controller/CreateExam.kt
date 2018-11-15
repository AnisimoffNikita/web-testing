package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.QuestionType
import com.bmstu.testingsystem.domain.Exam
import com.bmstu.testingsystem.domain.Question
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

    @GetMapping("/create_exam")
    fun getCreateTest(model: Model, authentication: Authentication?): String {
        if (authentication == null)
            return "redirect:/sign_in"
        model.addAttribute("exam", ExamData())
        return "create_exam"
    }

    @PostMapping("/create_exam")
    fun postCreateTest(model: Model, @ModelAttribute exam: ExamData, authentication: Authentication?): String {
        if (authentication == null)
            return "redirect:/sign_in"
        
        val username = authentication.name
        val  owner = userService.findByUsername(username)
        testService.addExam(exam, owner)

        model.addAttribute("exam", ExamData()) // временный фикс
        return "create_exam" // todo
    }
}

data class ExamData (
        var name: String = "",
        var description: String = "",
        var questions: MutableList<QuestionData> = arrayListOf()
) {
    fun toExam(user: User) : Exam {
        val i = 0
        val questionList:  MutableList<Question> = arrayListOf()
        for (q in questions) {
            questionList.add(q.toQuestion(i))
            i.inc()
        }
        return Exam(user, name, description, java.sql.Date(System.currentTimeMillis()), questionList);
    }
}

data class QuestionData (
        var questionText: String = "",
        var type: String = "",
        var variants: MutableList<String>? = arrayListOf(),
        var correctVariants: MutableList<Int>? = arrayListOf(),
        var correctInputAnswer: String? = ""
) {
    fun toQuestion(id: Int) : Question {
        if (variants != null && variants!!.isEmpty())
            variants = null
        if (correctVariants != null && correctVariants!!.isEmpty())
            correctVariants = null
        if (correctInputAnswer != null && correctInputAnswer!!.isEmpty())
            correctInputAnswer = null
        val q = Question(id, questionText, convertType(), variants, correctVariants, correctInputAnswer)
        return q
    }

    fun convertType() = when (type) {
            "Один ответ" -> QuestionType.SINGLE_ANSWER
            "Несколько ответов" -> QuestionType.MULTIPLE_ANSWER
            "Без выбора ответа" -> QuestionType.NO_ANSWER
            else -> throw IllegalArgumentException()
        }

}