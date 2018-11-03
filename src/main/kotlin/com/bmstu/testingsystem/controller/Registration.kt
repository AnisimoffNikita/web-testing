package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.domain.UserRole
import com.bmstu.testingsystem.repositiry.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.util.*
import java.security.Principal
import javax.servlet.http.HttpServletRequest
import javax.servlet.ServletException


@Controller
class Registration {

    @Autowired
    private lateinit var userRepository: UserRepository

    @GetMapping("/sign_up")
    fun registrationForm(model: Model, principal: Principal?): String {
        if (principal != null) {
            return "redirect:/"
        }
        model.addAttribute("regData", RegistrationData())
        return "sign_up"
    }

    @PostMapping("/sign_up")
    fun registrationSubmit(@ModelAttribute regData: RegistrationData, principal: Principal?, request: HttpServletRequest): String {
        val isExist = userRepository.findByUsername(regData.username!!)
        if (isExist != null) {
            return "redirect:/login"
        }
        val user = User(UUID.fromString("7c9db2c0-dd18-454d-a90a-5f142b9807de"), regData.username, regData.email!!, regData.password!!, UserRole.USER)
        userRepository.save(user)
        try {
            request.login(user.username, user.password)
        } catch (e: ServletException) {

        }
        return "redirect:/"
    }

    data class RegistrationData (
            val username: String? = null,
            val email: String? = null,
            val password: String? = null
    )

}