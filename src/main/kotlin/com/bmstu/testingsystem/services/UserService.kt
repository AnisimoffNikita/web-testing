package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.controller.EditProfile
import com.bmstu.testingsystem.controller.SignUp
import com.bmstu.testingsystem.domain.User


interface UserService {

    fun findByUsername(username: String): User?

    fun registerUser(registrationData: SignUp.RegistrationData): User?

    fun updateUser(user: User, newUserData: EditProfile.UserData): Boolean

    fun updateAvatar(user: User, path: String)

    fun getAvatar(user: User): String
}
