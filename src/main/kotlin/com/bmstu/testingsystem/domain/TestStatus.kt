package com.bmstu.testingsystem.domain

import org.springframework.security.core.GrantedAuthority

enum class TestStatus : GrantedAuthority {
    PENDING,
    APPROVED,
    REJECTED;

    override fun getAuthority(): String {
        return this.name
    }

}

