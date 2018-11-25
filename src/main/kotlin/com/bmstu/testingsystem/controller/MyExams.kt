package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.UserRole
import com.bmstu.testingsystem.form_data.getApproveReject
import com.bmstu.testingsystem.form_data.getCreatedPassed
import com.bmstu.testingsystem.form_data.getPassStatisticDelete
import com.bmstu.testingsystem.form_data.getUsersExamsNewExams
import com.bmstu.testingsystem.security.AppUserPrincipal
import com.bmstu.testingsystem.services.AuthenticationServiceImpl
import com.bmstu.testingsystem.services.ExamServiceImpl
import com.bmstu.testingsystem.services.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*

@Controller
class MyExams {

    @Autowired
    private lateinit var userService: UserServiceImpl

    @Autowired
    private lateinit var examService: ExamServiceImpl

    @Autowired
    private lateinit var authService: AuthenticationServiceImpl

    @GetMapping("/my_exams")
    fun getMyExams(model: Model, authentication: Authentication): String {
        val tst = (authentication.principal as AppUserPrincipal?)
        println(tst?.authorities)

        val user = authService.getUser(authentication)

        model.addAttribute("title", "Мои тесты")
        model.addAttribute("examLink", "exam_view")
        model.addAttribute("exams", user.exams)
        model.addAttribute("btns", getPassStatisticDelete())
        model.addAttribute("sidebar", getCreatedPassed(0))

        return "my_exams"
    }

    @GetMapping("/admin/new_exams")
    fun getNewExams(model: Model): String {
        fillModelForAdmin(model)
        return "my_exams"
    }

    @GetMapping("/my_exams/delete/{id}")
    fun deleteTest(@PathVariable id: UUID, model: Model, authentication: Authentication): String {
        val user = authService.getUser(authentication)
        if (!user.exams.map { it.id }.contains(id) && !authentication.authorities.contains(UserRole.ADMIN))
            return "redirect:/main_page"
        examService.removeExam(id)
        model.addAttribute("exams", user.exams)
        return "redirect:/my_exams"
    }

    @GetMapping("/admin/approve/{id}")
    fun approveExam(@PathVariable id: UUID, model: Model, authentication: Authentication): String {
        examService.approveExam(id)
        fillModelForAdmin(model)
        return "redirect:/admin/new_exams"
    }

    @GetMapping("/admin/reject/{id}")
    fun rejectExam(@PathVariable id: UUID, model: Model, authentication: Authentication): String {
        examService.rejectExam(id)
        fillModelForAdmin(model)
        return "redirect:/admin/new_exams"
    }

    private fun fillModelForAdmin(model: Model) {
        model.addAttribute("title", "Новые тесты")
        model.addAttribute("examLink", "admin/exam_view")
        model.addAttribute("exams", examService.getAllPendingExams())
        model.addAttribute("btns", getApproveReject())
        model.addAttribute("sidebar", getUsersExamsNewExams(2))
    }
}