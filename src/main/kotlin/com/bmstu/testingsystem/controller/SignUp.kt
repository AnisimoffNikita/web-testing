package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.Person
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.repositiry.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletException


@Controller
class SignUp {

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("/sign_up")
    fun registrationForm(model: Model, authentication: Authentication?): String {
        if (authentication != null) {
            return "redirect:/"
        }
        model.addAttribute("registration_data", RegistrationData())
        return "sign_up"
    }

    @PostMapping("/sign_up")
    fun registrationSubmit(@ModelAttribute rd: RegistrationData, request: HttpServletRequest): String {
        val isExist = userRepository.findByUsername(rd.username!!)
        if (isExist != null) {
            return "redirect:/login" // тут точно login?
        }
        val user = fromRegistrationData(rd)
        userRepository.save(user)
        try {
            request.login(user.username, user.password)
        } catch (e: ServletException) {
            return "redirect:/"
        }
        return "redirect:/"
    }

    data class RegistrationData (
        var username: String = "",
        var email: String = "",
        var password: String = ""
    )

    fun fromRegistrationData(rd: RegistrationData): User =
        User(rd.username, rd.email, rd.password)

}