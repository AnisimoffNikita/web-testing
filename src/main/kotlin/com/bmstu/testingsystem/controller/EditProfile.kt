package com.bmstu.testingsystem.controller

import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.domain.UserRole
import com.bmstu.testingsystem.form_data.*
import com.bmstu.testingsystem.security.AppUserPrincipal
import com.bmstu.testingsystem.services.AuthenticationServiceImpl
import com.bmstu.testingsystem.services.StorageService
import com.bmstu.testingsystem.services.UserServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.propertyeditors.CustomDateEditor
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import java.util.*
import java.text.SimpleDateFormat
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.bind.annotation.RequestParam
import org.apache.tomcat.jni.User.username
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority






@Controller
class EditProfile {

    @Autowired
    private lateinit var userService: UserServiceImpl

    @Autowired
    private lateinit var authService: AuthenticationServiceImpl


    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.registerCustomEditor(
                Date::class.java, CustomDateEditor(SimpleDateFormat("yyyy-MM-dd"), true)
        )
    }

    @GetMapping("/edit_profile")
    fun getEditProfile(model: Model, authentication: Authentication): String {
        val user = authService.getUser(authentication)
        val avatar = userService.getAvatar(user)

        model.addAttribute("user", UserData(user))
        model.addAttribute("avatar", avatar)
        return "edit_profile"
    }

    @PostMapping("/edit_profile")
    fun postEditProfile(@RequestParam("file") file: MultipartFile,
                        @ModelAttribute userData: UserData,
                        @ModelAttribute avatarData: String,
                        model: Model, authentication: Authentication): String {
        val oldUser = authService.getUser(authentication)

        val updatedUser = userService.updateUser(oldUser, userData)
        userService.updateAvatar(oldUser, avatarData, file)

        val nowAuthorities = SecurityContextHolder
                .getContext().authentication.authorities as Collection<UserRole>
        val a = UsernamePasswordAuthenticationToken(updatedUser.username, updatedUser.password, nowAuthorities)
        SecurityContextHolder.getContext().authentication = a

        val avatar = userService.getAvatar(oldUser)

        model.addAttribute("user", UserData(updatedUser))
        model.addAttribute("avatar", avatar)
        return "edit_profile"
    }

}