package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.form_data.UserData
import com.bmstu.testingsystem.services.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.PathVariable
import java.text.SimpleDateFormat
import java.util.*

@Controller
class ProfileView {

    @Autowired
    private lateinit var userService: UserServiceImpl

    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(SimpleDateFormat("yyyy-MM-dd"), true)
        )
    }

    @GetMapping("/profile_view/{name}")
    fun getEditProfile(@PathVariable name: String, model: Model): String {
        val user = userService.findByUsername(name)
        val avatar = userService.getAvatar(user)

        model.addAttribute("user", UserData(user))
        model.addAttribute("avatar", avatar)

        return "profile_view"
    }
}