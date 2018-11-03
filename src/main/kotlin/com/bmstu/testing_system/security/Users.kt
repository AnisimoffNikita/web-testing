package com.bmstu.testing_system.security

import com.bmstu.testing_system.model.User
import com.bmstu.testing_system.repositiry.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
internal class Users @Autowired
constructor(private val repo: UserRepository) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = repo.findByLogin(username) ?: throw UsernameNotFoundException(username)
        var auth = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER")
        if (username == "admin") {
            auth = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_ADMIN")
        }
        return org.springframework.security.core.userdetails.User(username, user.password, auth)
    }

}