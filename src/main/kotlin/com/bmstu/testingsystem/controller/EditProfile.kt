package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.User
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
import java.util.*
import java.text.SimpleDateFormat


@Controller
class EditProfile {

    @Autowired
    private lateinit var userService: UserServiceImpl

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(SimpleDateFormat("yyyy-MM-dd"), true)
        )
    }

    @GetMapping("/editprofile")
    fun getEditProfile(model: Model, authentication: Authentication?): String {
        if (authentication == null) {
            return "redirect:/"
        }
        val username = authentication.name
        val user = userService.findByUsername(username) ?: return "redirect:/"
        model.addAttribute("user", fromUser(user))
        return "editprofile"
    }

    @PostMapping("/editprofile")
    fun postEditProfile(@ModelAttribute userData: UserData, model: Model, authentication: Authentication?): String {
        if (authentication == null) {
            return "redirect:/"
        }
        val username = authentication.name
        val oldUser = userService.findByUsername(username) ?: return "redirect:/"
        userService.updateUser(oldUser, userData)
        model.addAttribute("user", userData)
        return "editprofile"
    }

    data class UserData (
            var username: String = "",
            var email: String = "",
            var password: String = "",
            var firstName: String? = "",
            var lastName: String? = "",
            var avatar: String? = "",
            var birthday: Date? = Date()
    )
        fun fromUser(user: User) : UserData = UserData(user.username, user.email, user.password,
                    user.person.firstName, user.person.lastName, user.person.avatar, user.person.birthday)

}