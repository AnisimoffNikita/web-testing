package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.UserRole
import com.bmstu.testingsystem.exception.NoPermissionException
import com.bmstu.testingsystem.form_data.adminGetCreatedPassed
import com.bmstu.testingsystem.form_data.getCreatedPassed
import com.bmstu.testingsystem.form_data.getUsersExamsNewExams
import com.bmstu.testingsystem.services.AuthenticationServiceImpl
import com.bmstu.testingsystem.services.ExamServiceImpl
import com.bmstu.testingsystem.services.TableServiceImpl
import com.bmstu.testingsystem.services.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import java.text.DateFormat
import java.util.*
import javax.servlet.http.HttpServletResponse

@Controller
class Table {

    @Autowired
    private lateinit var tableService: TableServiceImpl

    @Autowired
    private lateinit var authService: AuthenticationServiceImpl

    @Autowired
    private lateinit var examService: ExamServiceImpl

    @Autowired
    private lateinit var userService: UserServiceImpl

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                java.util.Date::class.java, CustomDateEditor(DateFormat.getInstance(), true)
        )
    }

    @GetMapping("/my_passed_exams")
    fun getMyPassedExams(model: Model, authentication: Authentication): String {
        val user = authService.getUser(authentication)
        model.addAttribute("title", "Пройденные тесты")
        model.addAttribute("table", tableService.getPassTableForUser(user))
        model.addAttribute("sidebar", getCreatedPassed(1))
        return "table"
    }

    @GetMapping("/admin/user_passed_exams/{name}")
    fun getUserPassedExams(@PathVariable name: String, model: Model): String {
        val user = userService.findByUsername(name) ?: throw IllegalArgumentException()
        model.addAttribute("title", "Пройденные тесты пользователя ${name}")
        model.addAttribute("table", tableService.getPassTableForUser(user))
        model.addAttribute("sidebar", adminGetCreatedPassed(1, name))
        return "table"
    }

    @GetMapping("/my_exams/results/{id}")
    fun getStatistic(@PathVariable id: UUID, model: Model, authentication: Authentication): String {
        val user = authService.getUser(authentication)
        if (!user.exams.map { it.id }.contains(id) && !authentication.authorities.contains(UserRole.ADMIN))
            throw NoPermissionException()

        val exam = examService.findById(id)

        model.addAttribute("title", "Результаты теста \"" + exam.name + "\"")
        model.addAttribute("table", tableService.getPassTableForExam(exam))

        return "table"
    }

    @GetMapping("/admin/all_users")
    fun getAllUsers(model: Model): String {
        model.addAttribute("title", "Все пользователи")
        model.addAttribute("table", tableService.getUserTable())
        model.addAttribute("sidebar", getUsersExamsNewExams(0))
        return "table"
    }

    @GetMapping("/admin/all_exams")
    fun getAllExams(model: Model): String {
        model.addAttribute("title", "Все тесты")
        model.addAttribute("table", tableService.getExamTable())
        model.addAttribute("sidebar", getUsersExamsNewExams(1))
        return "table"
    }

    @ExceptionHandler(Exception::class)
    fun exceptionHandler(model: Model,response: HttpServletResponse): String{
        model.addAttribute("info", "Некорректный запрос")
        response.status = HttpServletResponse.SC_BAD_REQUEST
        return "error_page"
    }
}
