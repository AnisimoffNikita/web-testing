package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.repositiry.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class EditProfile {

    @Autowired
    lateinit var userRepository: UserRepository

    @GetMapping("/editprofile")
    fun getEditProfile(model: Model, authentication: Authentication?): String {
        val username = authentication?.name;
        val user = userRepository.findByUsername(username!!);
        model.addAttribute("user", user);
        return "editprofile"
    }

    @PostMapping("/editprofile")
    fun postEditProfile(@ModelAttribute usr: User?, authentication: Authentication?, model: Model): String {

/*        val username = authentication?.name;
        val user = userRepository.findByUsername(username!!);*/
        //

        model.addAttribute("user", usr);

        return "editprofile"
    }
}