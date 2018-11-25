package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.UserRole
import com.bmstu.testingsystem.exception.NoPermissionException
import com.bmstu.testingsystem.form_data.getApproveReject
import com.bmstu.testingsystem.form_data.getDelete
import com.bmstu.testingsystem.form_data.getPassStatisticDelete
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
import org.springframework.web.bind.annotation.PathVariable
import java.text.DateFormat
import java.util.*


@Controller
class ExamView {

    @Autowired
    lateinit var examService: ExamServiceImpl

    @Autowired
    private lateinit var authService: AuthenticationServiceImpl

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(DateFormat.getInstance(), true)
        )
    }

    @GetMapping("/exam_view/{id}")
    fun getExamView(@PathVariable id: UUID, model: Model, authentication: Authentication): String {
        val user = authService.getUser(authentication)

        if (!user.exams.map { it.id }.contains(id) && !authentication.authorities.contains(UserRole.ADMIN))
            throw NoPermissionException()

        val exam = examService.findById(id)

        model.addAttribute("exam", exam)
        model.addAttribute("btnsForApproved", getPassStatisticDelete())
        model.addAttribute("btnsForOthers", getDelete())

        return "exam_view"
    }

    @GetMapping("/admin/exam_view/{id}")
    fun getExamViewAdmin(@PathVariable id: UUID, model: Model): String {
        val exam = examService.findById(id)

        model.addAttribute("exam", exam)
        model.addAttribute("btnsForApproved", getPassStatisticDelete())
        model.addAttribute("btnsForOthers", getApproveReject())

        return "exam_view"
    }
}