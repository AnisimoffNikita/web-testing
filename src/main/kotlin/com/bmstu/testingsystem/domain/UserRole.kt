package com.bmstu.testingsystem.domain

import org.springframework.security.core.GrantedAuthority

enum class UserRole constructor(private val role: String) : GrantedAuthority {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    override fun getAuthority(): String {
        return this.role
    }

}

