package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.controller.SignUp
import com.bmstu.testingsystem.domain.User
import com.bmstu.testingsystem.form_data.UserData


interface UserService {

    fun findByUsername(username: String): User?

    fun registerUser(registrationData: SignUp.RegistrationData): User?

    fun updateUser(user: User, newUserData: UserData): Boolean

    fun updateAvatar(user: User, path: String)

    fun getAvatar(user: User): String
}
