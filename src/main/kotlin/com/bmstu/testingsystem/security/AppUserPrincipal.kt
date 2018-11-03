package com.bmstu.testingsystem.security


import com.bmstu.testingsystem.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class AppUserPrincipal(private val user: User) : UserDetails {

    override fun getUsername(): String {
        return user.username
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf<GrantedAuthority>(user.role)
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}