package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.User
import org.springframework.security.core.Authentication

interface AuthenticationService {
    fun getUser(auth: Authentication?): User

    fun checkAuth(auth: Authentication?)
}