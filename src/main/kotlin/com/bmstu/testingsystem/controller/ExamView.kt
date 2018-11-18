package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.form_data.getApproveReject
import com.bmstu.testingsystem.form_data.getPassStatisticDelete
import com.bmstu.testingsystem.services.ExamServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import java.text.DateFormat
import java.util.*


@Controller
class ExamView {

    @Autowired
    lateinit var examService: ExamServiceImpl

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(DateFormat.getInstance(), true)
        )
    }

    // todo посылать нафиг юзера, если он не админ и хочет посмотреть ответы к чужому тесту
    @GetMapping("exam_view/{id}")
    fun getExamView(@PathVariable id: UUID, model: Model): String {
        val exam = examService.findById(id) ?: return "redirect:/main_page"
        model.addAttribute("exam", exam)
        model.addAttribute("btns", getPassStatisticDelete())
        return "exam_view"
    }

    @GetMapping("exam_view_admin/{id}")
    fun getExamViewAdmin(@PathVariable id: UUID, model: Model): String {
        val exam = examService.findById(id) ?: return "redirect:/main_page"
        model.addAttribute("exam", exam)
        model.addAttribute("btns", getApproveReject())
        return "exam_view"
    }
}