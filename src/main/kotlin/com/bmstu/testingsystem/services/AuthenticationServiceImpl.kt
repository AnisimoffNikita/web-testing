package com.bmstu.testingsystem.services

import com.bmstu.testingsystem.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import kotlin.IllegalStateException

@Service("authenticationService")
class AuthenticationServiceImpl : AuthenticationService {

    @Autowired
    private lateinit var userService: UserServiceImpl

    override fun getUser(auth: Authentication?): User {
        auth?: throw IllegalStateException()
        val username = auth.name
        val user = userService.findByUsername(username)
        user?: throw IllegalStateException()
        return user
    }

    override fun checkAuth(auth: Authentication?) {
        auth?: throw IllegalStateException()
    }
}