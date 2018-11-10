package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.User
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
import java.util.*
import java.text.SimpleDateFormat


@Controller
class EditProfile {

    @Autowired
    lateinit var userRepository: UserRepository

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(SimpleDateFormat("yyyy-MM-dd"), true)
        )
    }

    @GetMapping("/editprofile")
    fun getEditProfile(model: Model, authentication: Authentication?): String {
        val username = authentication?.name;
        val user = userRepository.findByUsername(username!!);
        model.addAttribute("user", user)
        return "editprofile"
    }

    @PostMapping("/editprofile")
    fun postEditProfile(@ModelAttribute user: User, model: Model, authentication: Authentication?): String {
        // добавить обновление данных в бд
        model.addAttribute("user", user)
        return "editprofile"
    }
}