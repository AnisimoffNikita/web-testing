package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.controller.SignUp
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.form_data.UserData
import org.springframework.web.multipart.MultipartFile


interface UserService {

    fun findByUsername(username: String): User?

    fun registerUser(registrationData: SignUp.RegistrationData): User?

    fun updateUser(user: User, newUserData: UserData): User

    fun updateAvatar(user: User, newAvatar: String, file: MultipartFile)

    fun getAvatar(user: User): String
}
