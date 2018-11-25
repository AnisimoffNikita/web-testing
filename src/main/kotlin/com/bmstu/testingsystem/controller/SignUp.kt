package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.services.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest


@Controller
class SignUp {

    @Autowired
    private lateinit var userService: UserServiceImpl

    @GetMapping("/sign_up")
    fun registrationForm(model: Model, authentication: Authentication?): String {
        if (authentication != null) {
            return "redirect:/main_page"
        }
        model.addAttribute("registration_data", RegistrationData())
        return "sign_up"
    }

    @PostMapping("/sign_up")
    fun registrationSubmit(@ModelAttribute rd: RegistrationData, request: HttpServletRequest): String {
        val user = userService.registerUser(rd) ?: return "redirect:/sign_up?exist=true"
        try {
            request.login(user.username, user.password)
        } catch (e: ServletException) {
            return "redirect:/sign_up?error=true"
        }
        return "redirect:/main_page"
    }

    data class RegistrationData (
        var username: String = "",
        var email: String = "",
        var password: String = ""
    )
}